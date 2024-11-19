<template>
    <div>
        <toolbar/>
        <div id="order-history-component">
            <h2 style="margin-top: 10%; color: #fc0339">NAME: XXXXXXXXXXXXXXXXXXXXX</h2>
            <hr/>
            <div id="profile-toolbar">
                <ul class="profile-toolbar-list">
                    <li><a href="#/profile/view">View</a></li>
                    <li><a href="">Edit</a></li>
                    <li class="active"><a  class="active" href="#/profile/orderHistory">Order History</a></li>
                </ul>
            </div>
            <div id="transactions">
                <div class="transaction">
                    <h3>Transaction ID</h3>
                    <p>Total amount: 30.54$</p>
                    <p>Date of purchase: 11/20/2024</p>
                    <p>Items bought: Socks...</p>
                </div>
            </div>
        </div>
    </div>
</template>


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

#transactions {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    width: 70%;
    text-align: left;
}

.transaction {
    width: 300px;
    border-width: 2px;
    border-color: lightpink;
    background-color: #ccc;
    border-radius: 15px;
    padding: 20px;
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