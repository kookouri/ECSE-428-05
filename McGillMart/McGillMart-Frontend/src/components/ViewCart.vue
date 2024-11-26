<template>
    <div>
        <toolbar />
        <div id="view-cart-component">
            <h2 style="margin-top: 10%; color: #fc0339">NAME: {{ user.name }}</h2>
            <hr />
            <div id="cart-container">
                <div v-if="cartItems.length > 0" id="cart-items">
                    <div class="cart-item" v-for="item in cartItems" :key="item.id">
                        <h3>{{ item.name }}</h3>
                        <img :src="item.url" alt="Item Image" />
                        <p><b>Price:</b> ${{ item.price }}</p>
                        <p><b>Description:</b> {{ item.description }}</p>
                    </div>
                    <div id="checkout-section">
                        <p><b>Total Items:</b> {{ cartItems.length }}</p>
                        <button @click="checkout" class="checkout-button">Check Out</button>
                    </div>
                </div>
                <div v-else>
                    <p>Your shopping cart is empty.</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import config from "../../config";

const backendUrl = "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

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
            fetch(backendUrl + `/users?email=${this.$cookies.get('username')}`, {
                method: "GET",
                redirect: "manual",
            })
                .then((response) => response.text())
                .then((data) => {
                    if (data) console.log("Data fetched:", data);
                    data = JSON.parse(data);

                    const account = data.accounts[0];
                    this.user.id = account.id; // Capture dynamic userId
                    this.user.name = account.name;
                    this.user.phoneNumber = account.phoneNumber;
                    this.user.email = account.email;

                    this.cartItems = account.shoppingCart || [];
                })
                .catch((error) => {
                    console.error("Error fetching user or cart:", error);
                });
        },
        checkout() {
            const userId = this.user.id; // Use dynamic userId from the fetched user data
            if (!userId) {
                console.error("User ID is missing, cannot proceed with checkout.");
                alert("Error: User ID is missing.");
                return;
            }

            const deletePromises = this.cartItems.map((item) =>
                fetch(`${backendUrl}/users/${userId}/shoppingCart/items/${item.id}`, {
                    method: "DELETE",
                })
            );

            Promise.all(deletePromises)
                .then((responses) => {
                    const allSuccessful = responses.every((res) => res.ok);
                    if (allSuccessful) {
                        alert("Checkout successful! Your cart is now empty.");
                        this.cartItems = []; // Clear cart on the frontend
                    } else {
                        alert("Some items could not be removed. Please try again.");
                    }
                })
                .catch((error) => {
                    console.error("Error during checkout:", error);
                    alert("Checkout failed. Please try again.");
                });
        },
    },
};
</script>

<style scoped>
#view-cart-component {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 20px;
}

#cart-container {
    width: 70%;
}

#cart-items {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    justify-content: center;
}

.cart-item {
    border: 1px solid #ccc;
    border-radius: 10px;
    padding: 15px;
    text-align: center;
    width: 30%;
    background-color: #f9f9f9;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.cart-item img {
    max-width: 100%;
    border-radius: 10px;
    margin-bottom: 10px;
}

#checkout-section {
    text-align: center;
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
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.checkout-button:hover {
    background-color: #0056b3;
}
</style>
