<template>
  <div id="home"> 
    <Toolbar @search-items="searchItems" />
    <Title />
    <ItemDisplay :searchQuery="searchQuery" />
    <Footer />
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchQuery: '',
    };
  },
  methods: {
    searchItems(query) {
      this.searchQuery = query;
    },
  },
};
</script>

<style scoped>
</style>
