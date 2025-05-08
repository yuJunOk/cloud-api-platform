import router from "@/router";

router.beforeEach((to, from, next) => {
  document.title = (to.meta.title as string) || "Default Title";
  next();
});
