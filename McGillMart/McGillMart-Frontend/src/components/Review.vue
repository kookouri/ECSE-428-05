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
        <select v-model="review.itemId" required>
          <option v-for="item in items" :key="item.id" :value="item.id">
            {{ item.name }}
          </option>
        </select>
      </div>
      <!-- Rating -->
      <div>
        <label for="rating">Rating:</label>
        <input
          type="number"
          min="1"
          max="5"
          v-model.number="review.rating"
          required
        />
      </div>
      <!-- Comment -->
      <div>
        <label for="comment">Comment:</label>
        <textarea v-model="review.comment" required></textarea>
      </div>
      <!-- Submit Button -->
      <button type="submit">Submit Review</button>
    </form>

    <p v-if="message" :class="{ success: isSuccess, error: !isSuccess }">
      {{ message }}
    </p>

    <!-- Section for displaying all items with their reviews -->
    <div class="item-reviews">
      <h2>All Items with Reviews</h2>
      <div v-for="item in itemsWithReviews" :key="item.id" class="item-review-card">
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

const frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
const backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const client = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  data() {
    return {
      isLoggedIn: false, // Data property to track login status
      review: {
        email: "",
        password: "",
        phoneNumber: "",
        itemId: null,
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
    this.checkLoginStatus(); // Check if the user is logged in
    await this.fetchItems();
    await this.fetchItemsWithReviews();
  },
  methods: {
    checkLoginStatus() {
      const username = this.$cookies.get("username");
      const password = this.$cookies.get("password");

      // Set isLoggedIn based on the presence of username and password cookies
      this.isLoggedIn = !!username && !!password;
      console.log("Is Logged In:", this.isLoggedIn);
    },
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
        this.message = "Error fetching user data.";
        this.isSuccess = false;
        console.error("Error fetching user:", error);
      }
    },
    async fetchItems() {
      try {
        const response = await fetch(`${backendUrl}/items`, {
          method: "GET",
          redirect: "manual",
        });
        const data = await response.json();
        this.items = data; // Store items in `items`
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
            const response = await axios.get(`${backendUrl}/items/${item.id}/reviews`);
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
        const {
          email,
          phoneNumber,
          password,
          itemId,
          rating,
          comment,
        } = this.review;

        const userRequest = { email, phoneNumber, password };
        const reviewRequest = { itemName: this.items.find((item) => item.id === itemId).name, rating, comment };

        const response = await axios.post(
          `${backendUrl}/items/${itemId}/reviews/`,
          reviewRequest,
          {
            headers: { "Content-Type": "application/json" },
          }
        );

        if (response.status >= 200 && response.status < 300) {
          this.message = "Review submitted successfully!";
          this.isSuccess = true;
          this.clearForm();
          await this.fetchItemsWithReviews(); // Refresh the list of reviews
        } else {
          this.message = "Failed to submit review.";
          this.isSuccess = false;
        }
      } catch (error) {
        this.message =
          error.response && error.response.data && error.response.data.message
            ? `Error: ${error.response.data.message}`
            : `Error: ${error.message}`;
        this.isSuccess = false;
        console.error(
          "Error posting review:",
          error.response ? error.response.data : error
        );
      }
    },
    clearForm() {
      this.review.itemId = null;
      this.review.rating = null;
      this.review.comment = "";
    },
    formatDate(dateString) {
      const options = { year: "numeric", month: "long", day: "numeric" };
      return new Date(dateString).toLocaleDateString(undefined, options);
    },
    goHome() {
      // Navigate to the home page
      this.$router.push("/");
    },
    goToSignIn() {
      // Navigate to the sign-in page
      this.$router.push("/login");
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

button:hover {
  background-color: #0056b3;
}

.success {
  color: green;
}

.error {
  color: red;
}

.review {
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 10px;
}

.item-reviews {
  margin-bottom: 20px;
}
</style>
