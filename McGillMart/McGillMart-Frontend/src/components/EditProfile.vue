<template>
    <div>
        <toolbar/>
        <div id="edit-profile-component">
            <h2 style="margin-top: 10%; color: #fc0339">EDIT PROFILE</h2>
            <hr/>
            <div id="profile-toolbar">
                <ul class="profile-toolbar-list">
                    <li><a href="#/profile/view">View</a></li>
                    <li class="active"><a class="active">Edit</a></li>
                    <li><a href="#/profile/order_history">Order History</a></li>
                </ul>
            </div>
            <form id="edit-profile-form" @submit.prevent="updateUser">
                <div class="form-group">
                    <label for="name"><b>Name:</b></label>
                    <input type="text" id="name" v-model="user.name" placeholder="Enter your name" required />
                </div>
                <div class="form-group">
                    <label for="email"><b>Email:</b></label>
                    <input type="email" id="email" v-model="user.email" placeholder="Enter your email" required />
                </div>
                <div class="form-group">
                    <label for="phoneNumber"><b>Phone Number:</b></label>
                    <input type="tel" id="phoneNumber" v-model="user.phoneNumber" placeholder="Enter your phone number" required />
                </div>
                <div class="form-group">
                    <label for="password"><b>New Password:</b></label>
                    <input type="password" id="password" v-model="password.new" placeholder="Enter new password" />
                </div>
                <div class="form-group">
                    <label for="confirmPassword"><b>Confirm New Password:</b></label>
                    <input type="password" id="confirmPassword" v-model="password.confirm" placeholder="Confirm new password" />
                </div>
                <button type="submit" class="save-button">Save Changes</button>
            </form>
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
                name: "",
                phoneNumber: "",
                email: ""
            },
            password: {
                new: "",
                confirm: ""
            }
        };
    },
    mounted() {
        this.fetchUser();
    },
    methods: {
        fetchUser() {
            fetch(`${backendUrl}/users?email=${this.$cookies.get('username')}`, {
                method: 'GET',
                redirect: 'manual'
            })
            .then(response => response.json())
            .then(data => {
                const account = data.accounts[0];
                this.user.id = account.id;
                this.user.name = account.name;
                this.user.phoneNumber = account.phoneNumber;
                this.user.email = account.email;
                this.user.password = this.$cookies.get('password');
            })
            .catch(error => {
                console.error('Error fetching user:', error);
                alert("Failed to fetch user data. Please try again.");
            });
        },
        updateUser() {
            if (this.password.new && this.password.new !== this.password.confirm) {
                alert("Passwords do not match!");
                return;
            }

            const updateData = {
                id: this.user.id,
                email: this.user.email,
                name: this.user.name,
                phoneNumber: this.user.phoneNumber
            };
            if (this.password.new) {
                updateData.password = this.password.new; 
            } else {
                updateData.password = this.user.password; 
            }

            fetch(`${backendUrl}/users/${this.user.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(updateData)
            })
            .then(async response => {
                console.log("Response status:", response.status);
                if (response.ok) {
                    alert("Profile updated successfully!");
                } else {
                    const error = await response.json(); 
                    console.log("Error response:", error);
                    alert(error.message || "An error occurred");
                }
            })
            .catch(error => {
                console.error('Error during update:', error);
                alert("An unexpected error occurred. Please try again.");
            });
        }
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

#edit-profile-component {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 100%;
}

form {
    display: flex;
    flex-direction: column;
    width: 70%;
    text-align: left;
}

.form-group {
    margin-bottom: 1em;
}

label {
    display: block;
    margin-bottom: 0.5em;
    color: #333;
}

input {
    width: 100%;
    padding: 0.5em;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.save-button {
    background-color: #fc0339;
    color: #fff;
    padding: 0.5em 1em;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    margin-bottom: 2em; 
}

.save-button:hover {
    background-color: #b40027;
}

</style>
