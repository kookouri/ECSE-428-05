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
      <div
        v-for="item in itemsWithReviews"
        :key="item.name"
        class="item-review-card"
      >
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
    await this.fetchItemsWithReviews();
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
        const response = await fetch(`${backendUrl}/items`, {
          method: "GET",
          redirect: "manual",
        });
        const data = await response.json();
        this.items = data; // Store items in `items`
      } catch (error) {
        console.error("Error fetching items:", error);
      }
    },
    async fetchItemsWithReviews() {
      try {
        // Ensure that we have a list of item names
        const itemNames = this.items.map((item) => item.name);
        console.log("item names: ", itemNames);

        for (const item of this.items) {
          console.log("what is item.name here?", item.name);
          console.log("what is encoded?", encodeURIComponent(item.name));
          console.log("URL:", `${backendUrl}/items/${encodeURIComponent(item.name)}/reviews`);
          const response = await fetch(
            `${backendUrl}/items/${encodeURIComponent(item.name)}/reviews`,
            {
              method: "GET",
              redirect: "manual",
            }
          );

          console.log("RESPONSE:", response);
          // Handle the response from each item
          if (response.ok) {
            const data = await response.json();
            console.log(`Reviews for ${item.name}:`, data);
          } else {
            console.error(`Failed to fetch reviews for ${item.name}`);
          }
        }

        const data = await response.json();
        console.log("WHAT IS DATA: ", data);

        // Process the returned data
        // this.itemsWithReviews = this.items.map(item => {
        //   const itemReviews = data.find(d => d.itemName === item.name);
        //   return {
        //     ...item,
        //     reviews: itemReviews ? itemReviews.reviews : [],
        //   };
        // });
      } catch (error) {
        console.error("Error fetching items with reviews:", error);
      }
    },
    async submitReview() {
      try {
        const { email, phoneNumber, password, itemName, rating, comment } =
          this.review;

        const userRequest = { email, phoneNumber, password };
        const reviewRequest = { itemName, rating, comment };

        const response = await client.post(
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
