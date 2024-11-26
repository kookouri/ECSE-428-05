<template>
  <div class="leave-review">
    <h2>Leave a Review</h2>
    <form @submit.prevent="submitReview">
      <!-- User Email -->
      <div>
        <label for="email">Email:</label>
        <input type="email" v-model="review.email" readonly />
      </div>
      <!-- User Phone Number -->
      <div>
        <label for="phoneNumber">Phone Number:</label>
        <input type="text" v-model="review.phoneNumber" readonly />
      </div>
      <!-- User Password -->
      <div>
        <label for="password">Password:</label>
        <input type="password" v-model="review.password" readonly />
      </div>
      <!-- Item Name -->
      <div>
        <label for="itemName">Item Name:</label>
        <select v-model="review.itemName" required>
          <option v-for="item in items" :key="item.id" :value="item.name">
            {{ item.name }}
          </option>
        </select>
      </div>
      <!-- Rating -->
      <div>
        <label for="rating">Rating:</label>
        <input type="number" min="1" max="5" v-model.number="review.rating" required />
      </div>
      <!-- Comment -->
      <div>
        <label for="comment">Comment:</label>
        <textarea v-model="review.comment" required></textarea>
      </div>
      <!-- Submit Button -->
      <button type="submit">Submit Review</button>
    </form>

    <p v-if="message" :class="{'success': isSuccess, 'error': !isSuccess}">{{ message }}</p>

    <!-- Section for displaying all items with their reviews -->
    <div class="item-reviews">
      <h2>All Items with Reviews</h2>
      <div v-for="item in itemsWithReviews" :key="item.name" class="item-review-card">
        <h3>{{ item.name }}</h3>
        <p><strong>Price:</strong> ${{ item.price }}</p>
        <p><strong>Description:</strong> {{ item.description }}</p>
        <div v-if="item.reviews && item.reviews.length > 0">
          <h4>Reviews:</h4>
          <ul>
            <li v-for="review in item.reviews" :key="review.id">
              <strong>{{ review.userName }}</strong> ({{ review.date }}): 
              <em>"{{ review.comment }}"</em> - Rated: {{ review.rating }}/5
            </li>
          </ul>
        </div>
        <div v-else>
          <p>No reviews yet for this item.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import config from "../../config";

const backendUrl = "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

export default {
  data() {
    return {
      review: {
        email: "",
        phoneNumber: "",
        password: "",
        itemName: "",
        rating: null,
        comment: "",
      },
      items: [],
      itemsWithReviews: [], // To store items and their reviews
      message: "",
      isSuccess: true,
    };
  },
  async mounted() {
    await this.fetchUser();
    await this.fetchItems();
    await this.fetchItemsWithReviews(); // Fetch reviews for all items
  },
  methods: {
    async fetchUser() {
      try {
        const response = await fetch(
          `${backendUrl}/users?email=${this.$cookies.get("username")}`,
          {
            method: "GET",
            redirect: "manual",
          }
        );

        const data = await response.text();
        const parsedData = JSON.parse(data);

        this.review.email = parsedData.accounts[0].email;
        this.review.phoneNumber = parsedData.accounts[0].phoneNumber;
        this.review.password = this.$cookies.get("password");
      } catch (error) {
        console.error("Error fetching user:", error);
      }
    },
    async fetchItems() {
      try {
        const response = await axios.get(backendUrl + "/items");
        this.items = response.data;
      } catch (error) {
        this.message = "Failed to load items.";
        this.isSuccess = false;
        console.error("Error fetching items:", error);
      }
    },
    async fetchItemsWithReviews() {
      try {
        const itemReviews = await Promise.all(
          this.items.map(async (item) => {
            const response = await axios.get(`${backendUrl}/items/${item.name}/reviews`);
            return { ...item, reviews: response.data };
          })
        );
        this.itemsWithReviews = itemReviews;
      } catch (error) {
        console.error("Error fetching items with reviews:", error);
      }
    },
    async submitReview() {
      try {
        const { email, phoneNumber, password, itemName, rating, comment } = this.review;

        const userRequest = { email, phoneNumber, password };
        const reviewRequest = { itemName, rating, comment };

        const response = await axios.post(
          `${backendUrl}/items/${itemName}/reviews/`,
          null,
          {
            params: { user: userRequest, review: reviewRequest },
          }
        );

        if (response.status === 200 || response.status === 201) {
          this.message = "Review submitted successfully!";
          this.isSuccess = true;
          this.clearForm();
          await this.fetchItemsWithReviews(); // Refresh the list of reviews
        } else {
          this.message = "Failed to submit review.";
          this.isSuccess = false;
        }
      } catch (error) {
        this.message = `Error: ${error.message}`;
        this.isSuccess = false;
      }
    },
    clearForm() {
      this.review.itemName = "";
      this.review.rating = null;
      this.review.comment = "";
    },
  },
};
</script>

<style scoped>
.leave-review {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

.item-reviews {
  margin-top: 30px;
  padding: 20px;
}

.item-review-card {
  margin-bottom: 20px;
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 5px;
}

h3 {
  margin-bottom: 5px;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin-bottom: 10px;
}

.success {
  color: green;
}

.error {
  color: red;
}
</style>
