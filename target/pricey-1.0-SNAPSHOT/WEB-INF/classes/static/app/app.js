(function(angular) {
    angular.module("app.controllers", []);
    angular.module("app.services", []);
    angular.module("app", ["ngResource", "app.controllers", "app.services"]);
}(angular));