import Vue from 'vue';


export const store = Vue.observable({
    value: []
});

export const mutations = {
    addValue(newValue) {
        store.value.push(newValue);
    },

    removeValue(value){
        store.value.splice(store.value.indexOf(value), 1);
    }
};
