<template>
    <div>
        <toolbar/>
        <div id="order-history-component">
            <h2 style="margin-top: 10%; color: #fc0339">NAME: {{ this.$cookies.get('name') }}</h2>
            <hr/>
            <div id="profile-toolbar">
                <ul class="profile-toolbar-list">
                    <li><a href="#/profile/view">View</a></li>
                    <li><a href="#/profile/edit">Edit</a></li>
                    <li class="active"><a  class="active" href="#/profile/order_history">Order History</a></li>
                </ul>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-md-4 mb-4" 
                        v-for="(transaction, index) in transactions.slice().reverse()" 
                        :key="index"
                    >
                        <div class="card text-left p-4 rounded bg-light">
                            <h3>Transaction #{{ transactions.length - index }}</h3>
                            <p>Total amount: ${{ transaction.amount }}</p>
                            <p>Date of purchase: {{ transaction.dateOfPurchase }}</p>
                            <p>{{ transaction.description }}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
const backendUrl = "http://127.0.0.1:8080";

export default {
    data() {
        return {
            user: {
                id: -1,
            },
            transactions: [] // To store fetched transactions
        };
    },
    mounted() {
        // Fetch accounts data when the component is created
        this.fetchTransactions();
    },
    methods: {
        fetchTransactions() {
            fetch(backendUrl + `/users/${this.$cookies.get('id')}/transactions`, {
                method: 'GET',
                redirect: 'manual' // Prevents automatic following of redirects
            })
            .then(response => {
                if (response.status === 302) {
                    const redirectUrl = response.headers.get('Location');
                    if (redirectUrl) {
                        console.log("Redirecting to:", redirectUrl);
                        return fetch(redirectUrl); // Follow the redirect
                    } else {
                        console.error("Redirection location is missing.");
                    }
                } else {
                    
                    return response.json(); // Handle regular response
                }
            })
            .then(data => {
                this.transactions = data.transactions; // Assuming the API returns an array of transactions
            })
            .catch(error => {
                console.error('Error fetching transactions:', error);
            });
        },
    }
};
</script>


<style scoped>

p {
    margin-bottom: 0%;    
}

#profile-toolbar {
    margin-bottom: 1em;
    display: flex;
    flex-direction: row;
    border-bottom: 1px #ccc solid;
    width: 70%;
}

#profile-toolbar ul {
    list-style-type: none;
    margin: 0;
    margin-right: 0.2%;
    margin-bottom: -0.1%;
    padding: 0;
    overflow: hidden;
    width: 70%;
}

#profile-toolbar li {
    float: left;
    padding-right: 0.5%;
}

#profile-toolbar li .active {
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
    border: #ccc 1px solid;
    border-bottom: 1px #ffff solid;
}

#profile-toolbar li a {
    display: block;
    color: #fc0339;
    text-align: center;
    padding-top: 4px;
    padding-bottom: 4px;
    padding-left: 6px;
    padding-right: 6px;
    text-decoration: none;
}

#profile-toolbar li a:hover:not(.active) {
    background-color: #f4f4f4;
    border-top-left-radius: 10%;
    border-top-right-radius: 10%;
    border: #f4f4f4 0px solid;
    color: #b40027;
}

#order-history-component {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 100%;
}

hr {
    display: block;
    height: 1px;
    border: 0;
    border-top: 1px solid #f1f1f1;
    margin-top: 0.2%;
    margin-bottom: 1%;
    padding: 0;
    width: 70%;
}
</style>