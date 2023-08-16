import Vuex from 'vuex'
import { member } from './member'

export const store = new Vuex.Store({
    modules: {
        member
    }
})