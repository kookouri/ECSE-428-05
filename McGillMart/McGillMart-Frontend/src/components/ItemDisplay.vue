<template>
  <div class="items">
    <hr>
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
      <div
        v-for="item in filteredItems"
        :key="item.id"
        class="item-card p-2"
      >
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

          <!-- Reviews Link -->
          <div class="row mb-2 mx-1">
            <strong>Reviews: </strong>
            <a @click="goToReviewPage(item.id)" class="review-link">
              {{ item.reviewCount }}
            </a>
          </div>

          <!-- Reviews Dropdown -->
          <details class="reviews">
            <summary>Show Reviews</summary>
            <div v-if="item.reviews && item.reviews.length">
              <div
                v-for="review in item.reviews"
                :key="review.id"
                class="review"
              >
                <p><strong>Rating:</strong> {{ review.rating }}</p>
                <p><strong>Comment:</strong> {{ review.comment }}</p>
                <p><strong>By:</strong> {{ review.username }}</p>
              </div>
            </div>
            <div v-else>
              <p>No reviews for this item yet.</p>
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
  props: {
    searchQuery: {
      type: String,
      default: "",
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

        // Optionally, fetch reviews for each item
        await this.fetchReviewsForItems();
      } catch (error) {
        this.error = "Failed to load items";
        console.error(error);
      }
    },
    async fetchReviewsForItems() {
      for (const item of this.items) {
        try {
          const response = await axios.get(
            `http://127.0.0.1:8080/items/${item.id}/reviews/`
          );
          let reviews = response.data;

          // Check if the response is an array; if not, convert it
          if (!Array.isArray(reviews)) {
            if (reviews === null || reviews === "") {
              reviews = [];
            } else {
              reviews = [reviews];
            }
          }

          // Assign the reviews to the item
          item.reviews = reviews;

          // Update the review count
          item.reviewCount = reviews.length;
        } catch (error) {
          console.error(
            `Error fetching reviews for item ${item.name}:`,
            error
          );
          item.reviews = [];
          item.reviewCount = 0;
        }
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
    goToReviewPage(itemId) {
      // Navigate to the review page, adjust the route as necessary
      this.$router.push(`/review`);
    },
  },
};
</script>

<style scoped>
.items {
  padding: 20px;
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

.reviews {
  margin-top: 10px;
}

.review {
  border-bottom: 1px solid #ddd;
  padding: 5px 0;
}

.review:last-child {
  border-bottom: none;
}

.error {
  color: red;
  text-align: center;
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

.review-link {
  color: #007bff;
  cursor: pointer;
  text-decoration: underline;
  margin-left: 5px;
}

.review-link:hover {
  color: #0056b3;
}

details summary {
  cursor: pointer;
  font-weight: bold;
  margin-top: 10px;
}

@media (max-width: 768px) {
  .item-card {
    width: 100%;
  }
}
</style>
