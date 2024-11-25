<template>
    <div class="leave-review">
      <h2>Leave a Review</h2>
      <form @submit.prevent="submitReview">
        <div>
          <label for="email">Email:</label>
          <input type="email" v-model="review.email" required />
        </div>
        <div>
          <label for="itemName">Item Name:</label>
          <select v-model="review.itemName" required>
            <option v-for="item in items" :key="item.id" :value="item.name">
              {{ item.name }}
            </option>
          </select>
        </div>
        <div>
          <label for="rating">Rating:</label>
          <input type="number" min="1" max="5" v-model.number="review.rating" required />
        </div>
        <div>
          <label for="comment">Comment:</label>
          <textarea v-model="review.comment" required></textarea>
        </div>
        <button type="submit">Submit Review</button>
      </form>
  
      <p v-if="message" :class="{'success': isSuccess, 'error': !isSuccess}">{{ message }}</p>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  import config from "../../config";
  
  const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port;
  const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
  
  const client = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl },
  });
  
  export default {
    data() {
      return {
        review: {
          email: "",
          itemName: "",
          rating: null,
          comment: "",
        },
        items: [],
        message: "",
        isSuccess: true,
      };
    },
    async created() {
      await this.fetchItems();
    },
    methods: {
      async fetchItems() {
        try {
          const response = await client.get("/items");
          this.items = response.data;
        } catch (error) {
          this.message = "Failed to load items.";
          this.isSuccess = false;
          console.error(error);
        }
      },
      async submitReview() {
        try {
          const response = await client.post("/reviews", this.review);
          if (response.status === 200) {
            this.message = "Review submitted successfully!";
            this.isSuccess = true;
            this.clearForm();
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
        this.review = {
          email: "",
          itemName: "",
          rating: null,
          comment: "",
        };
      },
    },
  };
  </script>
  
  <style scoped>
  .leave-review {
    max-width: 400px;
    margin: 0 auto;
  }
  
  form {
    display: flex;
    flex-direction: column;
  }
  
  label {
    margin-top: 10px;
  }
  
  input, textarea, select {
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
  