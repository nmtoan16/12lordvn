/* Local replacement for discontinued SMS transactions. No telephone/network API. */
'use strict';
(() => {
  const protocol = 'com/sun/midp/io/j2me/sms/Protocol.';
  const queues = new Map();
  const connections = new Map();
  let preparedRequest = null;
  let listenerPort = 17530; // SQMidlet constructor; also follow open0 below.
  let nextHandle = 1;
  let transactions = 0;
  let acknowledgments = 0;

  function status() {
    if (parent !== window) parent.postMessage({
      app: 'loan12', type: 'status',
      message: 'Giao dịch mô phỏng miễn phí trên bản web. Không gửi SMS, không thu phí.'
    }, location.origin);
  }

  function queueFor(port) {
    if (!queues.has(port)) queues.set(port, {messages: [], listeners: [], receivers: []});
    return queues.get(port);
  }

  function notifyListener(queue) {
    const message = queue.messages.find(item => !item.notified);
    if (!message || !queue.listeners.length) return;
    message.notified = true;
    queue.listeners.shift().resolve(message.text.length * 2);
  }

  function enqueue(text) {
    const queue = queueFor(listenerPort);
    const message = {text, notified: false};
    acknowledgments++;
    if (queue.receivers.length) queue.receivers.shift().resolve(message);
    else {
      queue.messages.push(message);
      notifyListener(queue);
    }
  }

  function requestFrom(text) {
    if (typeof text !== 'string' || text.length > 1024) return null;
    // Outgoing messages carry v2. Incoming acknowledgments deliberately do not.
    const tokens = text.match(/"[^"]*"|\S+/g) || [];
    if (!/^[A-Za-z][A-Za-z0-9_]{0,63}$/.test(tokens[0] || '') || !tokens.includes('v2')) return null;
    const action = tokens.find(token => /^a[245]$/.test(token));
    if (!action) return null;
    const reason = tokens.find(token => /^r/.test(token));
    if (action === 'a4' && !/^rreco[0-9]$/.test(reason || '')) return null;
    if (action === 'a2' && reason && reason !== 'rshop') return null;
    return {prefix: tokens[0], action, slot: action === 'a4' ? Number(reason.slice(5)) : null};
  }

  function observeRequest(text) {
    const request = requestFrom(text);
    if (!request) return false;
    transactions++;
    status();
    if (request.action === 'a4') {
      // The service chose whether to award the hidden fourth collectible.
      // Use the existing bg.a award algorithm; only simulate that service choice.
      // Slots 3/7/9 allow the local collection to finish; no real prize is claimed.
      const action = [3, 7, 9].includes(request.slot) ? 1 : 4;
      const code = 'LOCAL-WEB-' + request.slot;
      const reply = request.prefix + ' a' + action +
        ' "lBộ sưu tập ảo trong bản web (không đổi thưởng thật)" c' + code + ' rreco' + request.slot;
      // Let cs.run finish its original success callback and waiting dialog first.
      setTimeout(() => enqueue(reply), 350);
    }
    return true;
  }

  function utf16Bytes(text) {
    const address = J2ME.newByteArray(text.length * 2);
    const bytes = J2ME.getArrayFromAddr(address);
    for (let index = 0; index < text.length; index++) {
      const char = text.charCodeAt(index);
      bytes[index * 2] = char >>> 8;
      bytes[index * 2 + 1] = char & 255;
    }
    return address;
  }

  // This supplied JAR removes MessageConnection.send() from cs.run. It still
  // constructs TextObject, sets its payload, then grants through the original
  // shop/lives callbacks. Observe that boundary without granting anything here.
  const payloadKey = 'com/sun/midp/io/j2me/sms/TextObject.setPayloadText.(Ljava/lang/String;)V';
  const setPayloadText = function (address, stringAddress) {
    const field = J2ME.getClassInfo(address).getFieldByName(J2ME.toUTF8('buffer'), J2ME.toUTF8('[B'), false);
    if (!stringAddress) {
      i32[(address + field.byteOffset) >> 2] = 0;
      return;
    }
    const text = J2ME.fromStringAddr(stringAddress);
    const bufferAddress = utf16Bytes(text);
    i32[(address + field.byteOffset) >> 2] = bufferAddress;
    if (observeRequest(text)) preparedRequest = {text, expiresAt: Date.now() + 1000};
  };
  ReplaceMethod[payloadKey] = setPayloadText;

  // Upstream's cold interpreter enters Java bytecode directly before consulting
  // ReplaceMethod. Install the exact same replacement when this one MethodInfo
  // is resolved, so the first purchase works as well as later JIT calls.
  function bindPayloadMethod(method) {
    if (method?.implKey !== payloadKey) return false;
    method.fn = setPayloadText;
    method.state = 1; // J2ME.MethodState.Compiled: invoke JS instead of bytecode.
    J2ME.linkedMethods[method.id] = setPayloadText;
    return true;
  }
  const classPrototype = J2ME.ClassInfo.prototype;
  const getMethodByIndex = classPrototype.getMethodByIndex;
  classPrototype.getMethodByIndex = function (index) {
    const method = getMethodByIndex.call(this, index);
    if (this.getClassNameSlow() === 'com/sun/midp/io/j2me/sms/TextObject' && bindPayloadMethod(method)) {
      classPrototype.getMethodByIndex = getMethodByIndex;
    }
    return method;
  };
  for (const method of J2ME.methodIdToMethodInfoMap) {
    if (bindPayloadMethod(method)) { classPrototype.getMethodByIndex = getMethodByIndex; break; }
  }

  Native[protocol + 'open0.(Ljava/lang/String;II)I'] = function (address, hostAddress, msid, port) {
    const handle = nextHandle++;
    connections.set(handle, {port});
    if (!hostAddress && port > 0) listenerPort = port;
    return handle;
  };

  Native[protocol + 'waitUntilMessageAvailable0.(II)I'] = function (address, port, handle) {
    const queue = queueFor(port);
    const message = queue.messages.find(item => !item.notified);
    if (message) {
      message.notified = true;
      return message.text.length * 2;
    }
    asyncImpl('I', new Promise(resolve => queue.listeners.push({handle, resolve})));
  };

  function fillPacket(packetAddress, port, message) {
    const packet = getHandle(packetAddress);
    packet.message = utf16Bytes(message.text);
    const sender = 'LOCAL-WEB';
    packet.address = J2ME.newByteArray(sender.length);
    const senderBytes = J2ME.getArrayFromAddr(packet.address);
    for (let index = 0; index < sender.length; index++) senderBytes[index] = sender.charCodeAt(index);
    packet.port = port;
    packet.sentAt = Date.now();
    packet.messageType = 2; // UCS2; Protocol.receive invokes TextEncoder.toString.
    return message.text.length * 2;
  }

  Native[protocol + 'receive0.(IIILcom/sun/midp/io/j2me/sms/Protocol$SMSPacket;)I'] = function (address, port, msid, handle, packetAddress) {
    const queue = queueFor(port);
    if (queue.messages.length) {
      const result = fillPacket(packetAddress, port, queue.messages.shift());
      notifyListener(queue);
      return result;
    }
    const context = $.ctx;
    asyncImpl('I', new Promise(resolve => queue.receivers.push({handle, resolve: message => {
      if (!message) { resolve(0); return; }
      context.setAsCurrentContext();
      resolve(fillPacket(packetAddress, port, message));
    }})));
  };

  Native[protocol + 'close0.(III)I'] = function (address, port, handle) {
    connections.delete(handle);
    const queue = queueFor(port);
    for (const type of ['listeners', 'receivers']) {
      const closing = queue[type].filter(waiter => waiter.handle === handle);
      queue[type] = queue[type].filter(waiter => waiter.handle !== handle);
      for (const waiter of closing) waiter.resolve(type === 'listeners' ? 0 : null);
    }
    return 0;
  };

  // Replace, never delegate to, the upstream mozActivity/websms implementation.
  Native[protocol + 'send0.(IILjava/lang/String;II[B)I'] = function (address, handle, type, hostAddress, destinationPort, sourcePort, messageAddress) {
    const bytes = J2ME.getArrayFromAddr(messageAddress);
    const text = type === 2 ? new TextDecoder('utf-16be').decode(bytes) :
      Array.from(bytes, byte => String.fromCharCode(byte & 255)).join('');
    // Some other MIDlets could call send after preparing a TextObject. Avoid
    // acknowledging that same operation twice, without suppressing any later
    // purchase whose body happens to be identical (e.g. the same collectible).
    if (preparedRequest?.text === text && Date.now() <= preparedRequest.expiresAt) preparedRequest = null;
    else if (!observeRequest(text)) throw $.newIOException('SMS bên ngoài đã bị chặn trong bản cục bộ offline.');
    return bytes.length;
  };

  window.loanOfflineTransactions = Object.freeze({
    version: 1,
    stats: () => ({transactions, acknowledgments, pending: Array.from(queues.values()).reduce((sum, queue) => sum + queue.messages.length, 0)})
  });
})();
