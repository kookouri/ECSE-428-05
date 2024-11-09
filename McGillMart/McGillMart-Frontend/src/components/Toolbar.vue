<template>
  <div id="app">
    <div>
      <b-navbar toggleable="lg" type="dark" variant="danger">
        <b-navbar-brand href="#">McGillMart</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-form>
              <b-form-input
                size="sm"
                class="mr-sm-2"
                placeholder="Search"
                v-model="searchQuery"
                @input="onSearch"
              />
              <b-button size="sm" class="my-2 my-sm-0" type="button" @click="onSearch">Search</b-button>
            </b-nav-form>

            <b-nav-item-dropdown right>
              <!-- Using 'button-content' slot -->
              <template #button-content>
                <em>User</em>
              </template>
              <b-dropdown-item href="#">Profile</b-dropdown-item>
              <b-dropdown-item href="#">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>
    <router-view />
  </div>
</template>

<script>
import router from "@/router/index";

export default {
  data() {
    return {
      mounted: false,
      toolbarColor: "#FFD0D5",
      isLoggedIn: false,
      searchQuery: '',
    };
  },
  methods: {
    checkAuthenticationStatus() {
      // Check if user is authenticated based on your authentication logic
      const username = this.$cookies.get("username");
      const password = this.$cookies.get("password");

      console.log(`Username: ${username}`);
      console.log(`Password: ${password}`);
      return username && password;
    },
    signOut() {
      this.isLoggedIn = false;
      this.$cookies.remove("username");
      this.$cookies.remove("password");
      this.$cookies.remove("role");
      this.$cookies.remove("id");
      this.$router.push("/");
    },
    onSearch() {
    this.$emit('search-items', this.searchQuery);
    }
  },
};
</script>

<style scoped></style>
