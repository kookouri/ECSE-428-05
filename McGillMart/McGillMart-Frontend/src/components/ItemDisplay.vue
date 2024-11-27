<template>
  <div class="items">
    <hr />
    <h1 class="item-list-title">Item List</h1>

    <div class="search-bar">
      <input
        type="text"
        v-model="searchQuery"
        placeholder="Search for items..."
        class="search-input"
      />
    </div>

    <div v-if="error" class="error">{{ error }}</div>
    <div v-else-if="filteredItems.length" class="item-grid">
      <div v-for="item in filteredItems" :key="item.id" class="item-card p-2">
        <p><strong>{{ item.name }}</strong></p>
        <img :src="item.url" alt="Item Image" class="item-image" />
        <div class="container">
          <div class="row mx-1">
            <div><strong>Category:</strong> {{ item.category }}</div>
            <div class="ml-auto">
              <strong>Price:</strong> ${{ item.price.toFixed(2) }}
            </div>
          </div>
          <div class="row my-2 mx-1">
            <div class="description-content">
              {{ item.description }}
            </div>
          </div>
          <!-- Updated Reviews Link -->
          <div class="row mb-2 mx-1">
            <strong>
              <router-link
                :to="{ path: '/review' }"
                class="reviews-link"
              >
                Reviews
              </router-link>
            </strong>
            : {{ item.reviewCount }}
          </div>

          <!-- Reviews Dropdown -->
          <details v-if="item.reviews && item.reviews.length" class="reviews">
            <summary>Show Reviews</summary>
            <div
              v-for="review in item.reviews"
              :key="review.id"
              class="review"
            >
              <p><strong>{{ review.username }}</strong>: {{ review.comment }}</p>
            </div>
          </details>

          <button class="add-to-cart-button" @click="addToCart(item.id)">
            Add to Cart
          </button>
        </div>
      </div>
    </div>
    <div v-else>No items found.</div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ItemList",
  data() {
    return {
      items: [],
      error: null,
      searchQuery: "",
    };
  },
  computed: {
    filteredItems() {
      const query = this.searchQuery.toLowerCase();
      return this.items.filter(
        (item) =>
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
        const response = await axios.get("http://127.0.0.1:8080/items");
        this.items = response.data;

        // Fetch reviews for each item
        for (let item of this.items) {
          try {
            const reviewsResponse = await axios.get(
              `http://127.0.0.1:8080/items/${item.id}/reviews`
            );
            let reviewsData = reviewsResponse.data;

            // Handle the case where the response is a single object or null
            if (!Array.isArray(reviewsData)) {
              reviewsData = reviewsData ? [reviewsData] : [];
            }

            item.reviews = reviewsData;
            item.reviewCount = item.reviews.length;
          } catch (error) {
            console.error(
              `Error fetching reviews for item ${item.id}:`,
              error
            );
            item.reviews = [];
            item.reviewCount = 0;
          }
        }
      } catch (error) {
        this.error = "Failed to load items";
        console.error(error);
      }
    },
    async addToCart(itemId) {
      try {
        if (this.$cookies.get("id") === null) {
          alert("You need to log in");
        } else {
          await axios.post(
            `http://127.0.0.1:8080/users/${this.$cookies.get(
              "id"
            )}/shoppingCart/items/${itemId}`
          );
          alert("Item added to cart successfully!");
        }
      } catch (error) {
        alert("Failed to add item to cart");
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

.item-list-title {
  text-align: center;
}

.search-bar {
  margin-bottom: 20px;
  text-align: center;
}

.search-input {
  width: 60%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.search-input:focus {
  outline: none;
  border-color: #fc0339;
  box-shadow: 0 0 5px rgba(252, 3, 57, 0.5);
}

.item-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
}

.item-card {
  border: 1px solid #ccc;
  border-radius: 5px;
  width: 30%;
  max-width: 350px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.item-image {
  max-width: 100%;
  height: auto;
  margin-bottom: 10px;
  border-radius: 5px;
}

.description-content {
  max-height: 100px;
  overflow-y: auto;
  padding: 5px;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 4px;
  text-align: left;
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
  margin-top: 10px;
}

.add-to-cart-button:hover {
  background-color: #ff7a8a;
}

.error {
  color: red;
  text-align: center;
}

.reviews-link {
  color: #007bff;
  text-decoration: none;
}

.reviews-link:hover {
  text-decoration: underline;
}

.reviews {
  margin-top: 10px;
}

.review {
  margin-bottom: 5px;
  padding: 5px;
  border-bottom: 1px solid #ccc;
}

details summary {
  cursor: pointer;
  font-weight: bold;
  margin-bottom: 5px;
}

@media (max-width: 768px) {
  .item-card {
    width: 100%;
  }
}
</style>
