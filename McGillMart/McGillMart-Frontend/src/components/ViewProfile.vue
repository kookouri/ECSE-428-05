<template>
    <div>
        <toolbar/>
        <div id="view-profile-component">
            <h2 style="margin-top: 10%; color: #fc0339">NAME: {{user.name}}</h2>
            <hr/>
            <div id="profile-toolbar">
                <ul class="profile-toolbar-list">
                    <li class="active"><a class="active">View</a></li>
                    <li><a href="">Edit</a></li>
                    <li><a href="#/profile/orderHistory">Order History</a></li>
                </ul>
            </div>
            <div id="profile-values">
                <p><b>Email:</b></p>
                <p style="margin-bottom: 2%;"> {{user.email}} </p>
                <p><b>Phone Number:</b></p>
                <p style="margin-bottom: 2%;"> {{user.phoneNumber}} </p>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import config from "../../config";

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const client = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    data() {
        return {
            user: {
                // Sample Data -- For Testing
                // name: "JOE",
                // phoneNumber: "123-456-7890",
                // email: "Joe@gmail.com"
                name: "",
                phoneNumber: "",
                email: ""
            }
        }
    },
    async created() {
        try {
            // Assuming username is the user's email
            const response = await client.get('/users/account?email=' + this.$cookies.get('username'));
            if (response.data.accounts[0].errorMessage !== null) {
                alert(response.data.errorMessage);
            } else {
                this.user.name = response.data.accounts[0].name.toUpperCase();
                this.user.phoneNumber = response.data.accounts[0].phoneNumber;
                this.user.email = response.data.accounts[0].email;
            }
        } catch (e) {
            alert(e);
        }
    }
}

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
    border-top-left-radius: 10%;
    border-top-right-radius: 10%;
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

#view-profile-component {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 100%;
}

#profile-values {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    width: 70%;
    text-align: left;
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