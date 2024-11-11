<template>
  <div class="items">
    <hr>
    <h1 class="item-list-title">Item List</h1>
    <div v-if="error" class="error">{{ error }}</div>
    <div v-else-if="filteredItems.length" style="display: flex; justify-content: center; flex-wrap: wrap;">
      <div v-for="item in filteredItems" :key="item.id" class="item-card" style=" position:relative">
      <h3>{{ item.name }}</h3>
        <img :src=item.url alt="Item Image" style="max-width: 100%; height: 65%; min-height: 65%;">
      <div>
        <p><strong>Price:</strong> ${{ item.price.toFixed(2) }}</p>
        <p><strong>Description:</strong> {{ item.description }}</p>
        <p><strong>Category:</strong> {{ item.category }}</p>
        <p><strong>Reviews:</strong> {{ item.reviewCount }}</p>
        
        <div v-if="item.reviews.length">
          <div v-for="review in item.reviews" :key="review.id">{{ review.comment }}</div>
        </div>
      </div>

      </div>
    </div>
    <p v-else>No items found.</p>
  </div>
</template>

<script>
import axios from 'axios';

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
