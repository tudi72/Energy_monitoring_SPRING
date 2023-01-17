import React from 'react'
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import PropTypes from 'prop-types'
import difference from 'lodash/difference'

class SockJsClient extends React.Component {
    static defaultProps = {
      onConnect: () => {},
      onDisconnect: () => {},
      getRetryInterval: (count) => { return 1000 * count },
      options: {},
      headers: {},
      subscribeHeaders: {},
      autoReconnect: true,
      debug: false,
      heartbeat: 10000
    }
  
    static propTypes = {
      url: PropTypes.string.isRequired,
      options: PropTypes.object,
      topics: PropTypes.array.isRequired,
      onConnect: PropTypes.func,
      onDisconnect: PropTypes.func,
      getRetryInterval: PropTypes.func,
      onMessage: PropTypes.func.isRequired,
      headers: PropTypes.object,
      subscribeHeaders: PropTypes.object,
      autoReconnect: PropTypes.bool,
      debug: PropTypes.bool,
      heartbeat: PropTypes.number,
      heartbeatIncoming: PropTypes.number,
      heartbeatOutgoing: PropTypes.number,
      onConnectFailure: PropTypes.func
    }
  
    constructor (props) {
      super(props)
  
      this.state = {
        connected: false,
        // False if disconnect method is called without a subsequent connect
        explicitDisconnect: false
      }
  
      this.subscriptions = new Map()
      this.retryCount = 0
    }
  
    componentDidMount () {
      this._connect()
    }
  
    componentWillUnmount () {
      this.disconnect()
    }
  
    shouldComponentUpdate (nextProps, nextState) {
      return false
    }
  
    /* eslint camelcase: ["error", {allow: ["UNSAFE_componentWillReceiveProps"]}] */
  
    UNSAFE_componentWillReceiveProps (nextProps) {
      if (this.state.connected) {
        // Subscribe to new topics
        difference(nextProps.topics, this.props.topics)
          .forEach((newTopic) => {
            this._log('Subscribing to topic: ' + newTopic)
            this._subscribe(newTopic)
          })
  
        // Unsubscribe from old topics
        difference(this.props.topics, nextProps.topics)
          .forEach((oldTopic) => {
            this._log('Unsubscribing from topic: ' + oldTopic)
            this._unsubscribe(oldTopic)
          })
      }
    }
  
    render () {
      return null
    }
  
    _initStompClient = () => {
      // Websocket held by stompjs can be opened only once
      this.client = Stomp.over(new SockJS(this.props.url, null, this.props.options))
  
      this.client.heartbeat.outgoing = this.props.heartbeat
      this.client.heartbeat.incoming = this.props.heartbeat
  
      if (Object.keys(this.props).includes('heartbeatIncoming')) {
        this.client.heartbeat.incoming = this.props.heartbeatIncoming
      }
      if (Object.keys(this.props).includes('heartbeatOutgoing')) {
        this.client.heartbeat.outgoing = this.props.heartbeatOutgoing
      }
      if (!this.props.debug) {
        this.client.debug = () => {}
      }
    }
  
    _cleanUp = () => {
      this.setState({ connected: false })
      this.retryCount = 0
      this.subscriptions.clear()
    }
  
    _log = (msg) => {
      if (this.props.debug) {
        console.log(msg)
      }
    }
  
    _subscribe = (topic) => {
      if (!this.subscriptions.has(topic)) {
        const subscribeHeaders = Object.assign({}, this.props.subscribeHeaders)
        let sub = this.client.subscribe(topic, (msg) => {
          this.props.onMessage(this._processMessage(msg.body), msg.headers.destination)
        }, subscribeHeaders)
        this.subscriptions.set(topic, sub)
      }
    }
  
    _processMessage = (msgBody) => {
      try {
        return JSON.parse(msgBody)
      } catch (e) {
        return msgBody
      }
    }
  
    _unsubscribe = (topic) => {
      let sub = this.subscriptions.get(topic)
      sub.unsubscribe()
      this.subscriptions.delete(topic)
    }
  
    _connect = () => {
      this._initStompClient()
      this.client.connect(this.props.headers, () => {
        this.setState({ connected: true })
        this.props.topics.forEach((topic) => {
          this._subscribe(topic)
        })
        this.props.onConnect()
      }, (error) => {
        if (error) {
          if (Object.keys(this.props).includes('onConnectFailure')) {
            this.props.onConnectFailure(error)
          } else {
            this._log(error.stack)
          }
        }
        if (this.state.connected) {
          this._cleanUp()
          // onDisconnect should be called only once per connect
          this.props.onDisconnect()
        }
        if (this.props.autoReconnect && !this.state.explicitDisconnect) {
          this._timeoutId = setTimeout(this._connect, this.props.getRetryInterval(this.retryCount++))
        }
      })
    }

    connect = () => {
      this.setState({ explicitDisconnect: false })
      if (!this.state.connected) {
        this._connect()
      }
    }
  

    disconnect = () => {
      if (this._timeoutId) {
        clearTimeout(this._timeoutId)
        this._timeoutId = null
      }
      this.setState({ explicitDisconnect: true })
      if (this.state.connected) {
        this.subscriptions.forEach((subid, topic) => {
          this._unsubscribe(topic)
        })
        this.client.disconnect(() => {
          this._cleanUp()
          this.props.onDisconnect()
          this._log('Stomp client is successfully disconnected!')
        })
      }
    }

    sendMessage = (topic, msg, optHeaders = {}) => {
      if (this.state.connected) {
        this.client.send(topic, optHeaders, msg)
      } else {
        throw new Error('Send error: SockJsClient is disconnected')
      }
    }
  }
  
  export default SockJsClient