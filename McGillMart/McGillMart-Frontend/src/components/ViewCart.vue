<template>
    <div class="items">
    <toolbar style="position: absolute; top: 0; left: 0; width: 100%;"/>
      <hr>
      <h1 class="item-list-title">Item List</h1>
      <!-- <div v-if="error" class="error">{{ error }}</div> -->
      <div v-if="cart_items.length" style="display: flex; justify-content: center; flex-wrap: wrap;">
        <div v-for="item in cart_items" :key="item.id" class="item-card" style=" position:relative">
        <h3>{{ item.name }}</h3>
          <img :src=item.url alt="Item Image" style="max-width: 100%; height: 55%; min-height: 55%;">
        <div>
          <p><strong>Price:</strong> ${{ item.price.toFixed(2) }}</p>
          <p><strong>Description:</strong> {{ item.description }}</p>
          <p><strong>Category:</strong> {{ item.category }}</p>
          <p><strong>Reviews:</strong> {{ item.reviewCount }}</p>
          <p>
            <button @click="removeItemFromCart(item)">Remove from cart</button>
          </p>
          
          <div v-if="item.reviews.length">
            <div v-for="review in item.reviews" :key="review.id">{{ review.comment }}</div>
          </div>
        </div>
  
        </div>
      </div>
      <p v-else>No items in your cart.</p>
    </div>
  </template>
  
  <script>
//   import axios from 'axios';
  import { store, mutations } from './CartStore.js';
  
  
  export default {
    name: 'ItemList',
    props: {
      searchQuery: {
        type: String,
        default: '',
      },
    },
    data() {
      return {
        cart_items: [],
      };
    },
    
    created() {
      this.cart_items = store.value;
    },
    methods: {
    //   async fetchItems() {
    //     try {
    //       const response = await axios.get('http://127.0.0.1:8080/items');
    //       this.items = response.data;
    //     } catch (error) {
    //       this.error = 'Failed to load items';
    //       console.error(error);
    //     }
    //   },
  
      addItemToCart(item) {
        //this.cart_items.push(item);
        mutations.addValue(item);
      },

      removeItemFromCart(item) {
        const index = this.cart_items.indexOf(item);
        // if (index > -1) {
        //     this.cart_items.splice(index, 1);
        // }
        mutations.removeValue(item);
      },
  
      checkStore(item) {
        console.log('Checking store:', store.value);
      },
    },
  };
  </script>
  
  <style scoped>
  .items {
    padding: 20px;
  }
  .item-card {
    border: 1px solid #ccc;
    padding: 10px;
    margin-bottom: 15px;
    border-radius: 5px;
    width: 30%;
    height: 600px;
  }
  .error {
    color: red;
  }
  </style>
  