<template>
  <div class="leave-review">
    <!-- Home Button -->
    <button class="home-button" @click="goHome">Home</button>

    <!-- Conditionally display the Leave a Review form -->
    <div v-if="isLoggedIn">
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
    </div>
    <!-- Message and Sign In Button for users who are not signed in -->
    <div v-else class="not-logged-in">
      <p>Please sign in to leave a review.</p>
      <button class="sign-in-button" @click="goToSignIn">Sign In</button>
    </div>

    <!-- Display All Reviews Sorted by Item Name -->
    <h2>All Reviews</h2>
    <div
      v-for="itemReviews in reviewsByItem"
      :key="itemReviews.itemId"
      class="item-reviews"
    >
      <h3>{{ itemReviews.itemName }}</h3>
      <div v-if="itemReviews.reviews.length > 0">
        <!-- Dropdown menu to show/hide reviews -->
        <details>
          <summary>Show Reviews</summary>
          <div
            v-for="review in itemReviews.reviews"
            :key="review.id"
            class="review"
          >
            <p><strong>Rating:</strong> {{ review.rating }}</p>
            <p><strong>Comment:</strong> {{ review.comment }}</p>
            <p><strong>Date:</strong> {{ formatDate(review.datePosted) }}</p>
            <p><strong>By:</strong> {{ review.username }}</p>
          </div>
        </details>
      </div>
      <div v-else>
        <p>No reviews for this item yet.</p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import config from "../../config";

const backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

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
      reviewsByItem: [],
      message: "",
      isSuccess: true,
    };
  },
  async mounted() {
    this.checkLoginStatus(); // Check if the user is logged in
    await this.fetchItems();
    await this.fetchReviews();
    if (this.isLoggedIn) {
      await this.fetchUser();
    }
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
        const username = this.$cookies.get("username");
        const password = this.$cookies.get("password");

        console.log("Username Cookie:", username);
        console.log("Password Cookie:", password);

        const response = await axios.get(
          `${backendUrl}/users?email=${username}`
        );

        console.log("Response Data:", response.data);

        const accounts = response.data.accounts;

        if (accounts && accounts.length > 0) {
          const user = accounts[0];

          this.review.email = user.email;
          this.review.phoneNumber = user.phoneNumber;
          this.review.password = password;

          console.log("User Details:");
          console.log("Email:", this.review.email);
          console.log("Phone Number:", this.review.phoneNumber);
          console.log("Password:", this.review.password);
        } else {
          this.message = "User not found.";
          this.isSuccess = false;
          console.error("No accounts found in response data.");
        }
      } catch (error) {
        this.message = "Error fetching user data.";
        this.isSuccess = false;
        console.error("Error fetching user:", error);
      }
    },
    async fetchItems() {
      try {
        const response = await axios.get(backendUrl + "/items");
        this.items = response.data;

        // Sort items by name
        this.items.sort((a, b) => a.name.localeCompare(b.name));
      } catch (error) {
        this.message = "Failed to load items.";
        this.isSuccess = false;
        console.error("Error fetching items:", error);
      }
    },
    async fetchReviews() {
      try {
        this.reviewsByItem = [];

        // Fetch reviews for each item using item ID
        for (const item of this.items) {
          try {
            const response = await axios.get(
              `${backendUrl}/items/${item.id}/reviews/`
            );
            let reviews = response.data;

            // Check if the response is a single object or an array
            if (!Array.isArray(reviews)) {
              // If the response is null or empty, set reviews to an empty array
              if (reviews === null || reviews === "") {
                reviews = [];
              } else {
                // Convert the single review object into an array
                reviews = [reviews];
              }
            }

            // Sort reviews by datePosted from most recent to least recent
            reviews.sort(
              (a, b) => new Date(b.datePosted) - new Date(a.datePosted)
            );

            this.reviewsByItem.push({
              itemId: item.id,
              itemName: item.name,
              reviews: reviews,
            });
          } catch (error) {
            console.error(
              `Error fetching reviews for item ${item.name}:`,
              error
            );
            // If there's an error, still push an empty reviews array
            this.reviewsByItem.push({
              itemId: item.id,
              itemName: item.name,
              reviews: [],
            });
          }
        }
      } catch (error) {
        console.error("Error fetching reviews:", error);
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

        const reviewRequest = {
          email,
          phoneNumber,
          password,
          rating: String(rating),
          comment,
        };

        console.log("Posting review to backend:", reviewRequest);
        console.log(`POST URL: ${backendUrl}/items/${itemId}/reviews/`);

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

          // Refresh reviews after submitting a new one
          await this.fetchReviews();
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
  position: relative; /* Added to position the home button */
}

/* Home Button Styling */
.home-button {
  position: absolute;
  top: 10px;
  right: 10px;
  padding: 8px 12px;
  background-color: #fc0339;;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.home-button:hover {
  background-color: #0056b3;
}

/* Sign In Button Styling */
.not-logged-in {
  text-align: center;
  margin-top: 50px;
}

.sign-in-button {
  padding: 10px 15px;
  margin-top: 10px;
  background-color: #fc0339;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.sign-in-button:hover {
  background-color: #218838;
}

form {
  display: flex;
  flex-direction: column;
  margin-top: 50px; /* Adjusted to provide space for the home button */
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
  background-color: #fc0339;
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

.review {
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 10px;
}

.item-reviews {
  margin-bottom: 20px;
}
</style>
