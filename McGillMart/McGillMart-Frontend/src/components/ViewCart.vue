<template>
    <div>
      <toolbar />
      <div id="view-cart-component">
        <h2 class="user-name my-3">Shopping cart of {{ user.name }}</h2>
        <div id="cart-container">
          <div v-if="cartItems.length" id="cart-items" class="item-grid">
            <div v-for="item in cartItems" :key="item.id" class="item-card">
              <p class="item-name">{{ item.name }}</p>
              <img :src="item.url" alt="Item Image" class="item-image" />
              <div class="container">
                <div class="row mx-1">
                  <div><strong>Category:</strong> {{ item.category }}</div>
                  <div class="ml-auto"><strong>Price:</strong> ${{ item.price.toFixed(2) }}</div>
                </div>
                <div class="row my-2 mx-1 description-content">
                  {{ item.description }}
                </div>

                <div class="row mx-auto"> 
                  <button class="remove-from-cart-button  mx-auto" @click="removeFromCart(item)">Remove from Cart</button> 
                </div>
              </div>
            </div>
          </div>
          <div v-else>
            <p>Your shopping cart is empty.</p>
          </div>
        </div>
        <div id="checkout-section">
          <p><b>Total Items:</b> {{ cartItems.length }}</p>
          <button @click="checkout" class="checkout-button mb-5">Check Out</button>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  import config from "../../config";
  
  const backendUrl = `http://${config.dev.backendHost}:${config.dev.backendPort}`;
  const frontendUrl = `http://${config.dev.host}:${config.dev.port}`;
  
  const client = axios.create({
    baseURL: backendUrl,
    headers: { "Access-Control-Allow-Origin": frontendUrl },
  });
  
  export default {
    data() {
      return {
        user: {
          id: null,
          name: "",
          phoneNumber: "",
          email: "",
        },
        cartItems: [],
      };
    },
    mounted() {
      this.fetchUserAndCart();
    },
    methods: {
      fetchUserAndCart() {
        const username = this.$cookies.get("username");
        fetch(`${backendUrl}/users?email=${username}`, {
          method: "GET",
        })
          .then((response) => response.json())
          .then((data) => {
            const account = data.accounts[0];
            this.user = { ...account };
            this.cartItems = account.shoppingCart || [];
          })
          .catch((error) => console.error("Error fetching user/cart:", error));
      },
      async removeFromCart(item) {
        try {
          await client.delete(`/users/${this.$cookies.get("id")}/shoppingCart/items/${item.id}`);
          alert('Item removed from cart');
          this.fetchUserAndCart();
        } catch (error) {
          alert('Failed to remove item from cart', error);
        }
      },
      async checkout() {
        if (!this.user.id) {
          alert("Error: User ID is missing.");
          return;
        }
  
        try {
          await client.post(`/users/${this.$cookies.get("id")}/checkout`);
  
          this.fetchUserAndCart();
          alert("Checkout successful! Your cart is now empty.");

        } catch (error) {
          console.error("Error during checkout:", error);
          alert("Checkout failed. Please try again.");
        }
      },
    },
  };
  </script>
  
  <style scoped>
  /* General Layout */
  #view-cart-component {
    text-align: center;
  }
  
  .user-name {
    color: #fc0339;
  }
  
  /* Grid and Card Styling */
  .item-grid {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    justify-content: center;
  }
  
  .item-card {
    border: 1px solid #ccc;
    border-radius: 8px;
    width: 30%;
    max-width: 350px;
    padding: 15px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    text-align: left;
  }
  
  .item-name {
    font-weight: bold;
    margin-bottom: 10px;
  }
  
  .item-image {
    width: 100%;
    height: auto;
    border-radius: 5px;
    margin-bottom: 10px;
  }
  
  .description-content {
    max-height: 100px;
    overflow-y: auto;
    padding: 5px;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    border-radius: 4px;
  }
  
  /* Checkout Section */
  #checkout-section {
    margin-top: 20px;
  }
  
  .checkout-button {
    background-color: #007bff;
    color: white;
    padding: 15px 30px;
    border: none;
    border-radius: 5px;
    font-size: 16px;
    font-weight: bold;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  .checkout-button:hover {
    background-color: #0056b3;
  }

  .remove-from-cart-button {
  background-color: #fc0339;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  cursor: pointer;
  font-weight: bold;
  transition: background-color 0.3s ease;
}

.remove-from-cart-button:hover {
  background-color: #ff7a8a;
}
  </style>
  