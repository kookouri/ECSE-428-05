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
      <!-- Item -->
      <div>
        <label for="item">Item:</label>
        <select v-model="review.itemId" required>
          <option v-for="item in items" :key="item.id" :value="item.id">
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

    <p v-if="message" :class="{ success: isSuccess, error: !isSuccess }">{{ message }}</p>
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
        password: "",
        phoneNumber: "",
        itemId: null,
        rating: null,
        comment: "",
      },
      items: [],
      message: "",
      isSuccess: true,
    };
  },
  async mounted() {
    await this.fetchUser();
    await this.fetchItems();
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

        console.log("User Details:");
        console.log("Email:", this.review.email);
        console.log("Phone Number:", this.review.phoneNumber);
        console.log("Password:", this.review.password);
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
    async submitReview() {
      try {
        const { email, phoneNumber, password, itemId, rating, comment } = this.review;

        const reviewRequest = {
          email,
          phoneNumber,
          password,
          rating,
          comment,
        };

        // Log the data being sent and the endpoint
        console.log("Posting review to backend:", reviewRequest);
        console.log(`POST URL: ${backendUrl}/items/${itemId}/reviews/`);

        const response = await axios.post(
          `${backendUrl}/items/${itemId}/reviews/`,
          reviewRequest
        );

        if (response.status === 200 || response.status === 201) {
          this.message = "Review submitted successfully!";
          this.isSuccess = true;
          this.clearForm();
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
        console.error("Error posting review:", error);
      }
    },
    clearForm() {
      this.review.itemId = null;
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

form {
  display: flex;
  flex-direction: column;
}

label {
  margin-top: 10px;
}

input,
textarea,
select {
  width: 100%;
  padding: 8px;
  margin: 5px 0;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  padding: 10px 15px;
  margin-top: 15px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
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
</style>
