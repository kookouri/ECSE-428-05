<template>
  <div class="items">
    <hr>
    <h1 class="item-list-title">Item List</h1>
    <div v-if="error" class="error">{{ error }}</div>
    <div v-else-if="filteredItems.length" style="display: flex; justify-content: center; flex-wrap: wrap;">
      <div v-for="item in filteredItems" :key="item.id" class="item-card" style=" position:relative">
      <h3>{{ item.name }}</h3>
        <img :src=item.url alt="Item Image" style="max-width: 100%; height: 55%; min-height: 55%;">
      <div>
        <p><strong>Price:</strong> ${{ item.price.toFixed(2) }}</p>
        <p><strong>Description:</strong> {{ item.description }}</p>
        <p><strong>Category:</strong> {{ item.category }}</p>
        <p><strong>Reviews:</strong> {{ item.reviewCount }}</p>
        
        <div v-if="item.reviews.length">
          <div v-for="review in item.reviews" :key="review.id">{{ review.comment }}</div>
        </div>
      </div>
      <button class="add-to-cart-button" @click="addToCart(item.id)">Add to Cart</button>
      </div>
    </div>
    <p v-else>No items found.</p>
  </div>
</template>

<script>
import axios from 'axios';
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
      items: [],
      error: null,
      cart_items: [],
    };
  },
  computed: {
    filteredItems() {
      const query = this.searchQuery.toLowerCase();
      return this.items.filter(
        item =>
          item.name.toLowerCase().includes(query) ||
          item.description.toLowerCase().includes(query) ||
          item.category.toLowerCase().includes(query)
      );
    },
  },
  created() {
    this.fetchItems();
  },
  methods: {
    async fetchItems() {
      try {
        const response = await axios.get('http://127.0.0.1:8080/items');
        this.items = response.data;
      } catch (error) {
        this.error = 'Failed to load items';
        console.error(error);
      }
    },

    addItemToCart(item) {
      this.cart_items.push(item);
      mutations.addValue(item);
    },

    checkStore(item) {
      console.log('Checking store:', store.value);
    },

    async addToCart(itemId) {
      try {
        const response = await axios.post(`http://127.0.0.1:8080/users/${this.$cookies.get('id')}/shoppingCart/items/${itemId}`);
        alert('Item added to cart successfully!');
        const item = this.items.find(item => item.id === itemId);
        if (item) {
          this.addItemToCart(item);
        }
      } catch (error) {
        alert('Failed to add item to cart');
        console.error(error);
      }
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
  height: 750px;
}
.add-to-cart-button {
  background-color: #fc0339;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  cursor: pointer;
  font-size: 16px;
  font-weight: bold;
  transition: background-color 0.3s ease;
}

.add-to-cart-button:hover {
  background-color: #ff7a8a;
}
.error {
  color: red;
}
</style>
