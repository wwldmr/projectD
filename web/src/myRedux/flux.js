class Dispatcher {
    constructor() {
        this.callbacks = []
    }

    register(callback) {
        this.callbacks.push(callback)
    }

    dispatch(action) {
        this.callbacks.pop()
    }
}

export const dispatcher = new Dispatcher()

class FluxStore {
    constructor() {
        this.state = { value:0 }
        this.listeners = []

        dispatcher.register(action => {
            switch (action.type) {
                case "INCREMENT":
                    this.state.value++
                    this.emit()
                    break

                case "DECREMENT":
                    this.state.value--
                    this.emit()
            }
        })
    }

    subscribe(listener) {
        this.listeners.push(listener)
    }

    emit() {
        this.listeners.forEach(listener => listener(this.state))
    }
}

export const fluxStore = new FluxStore()
