(function(angular) {
    /* Product resource factory */
    var ProdFactory = function($resource) {
        return $resource('/products/:id', {id: '@id'});
    };
    
    /* Customer resource factory */
    var CustFactory = function($resource) {
       return $resource('/customers/:id', {id: '@id'});
    };

    /* Rebate list factory */
    var RebtFactory = function($resource) {
        return $resource('/rebates/:forall/:price/:prod/:pid/:cust/:cid', {pid: "@pid", cid: "@cid"}, {
            getAppliedRebate: {method: 'GET', isArray: true}
        });

    };

    ProdFactory.$inject = ['$resource'];
    CustFactory.$inject = ['$resource'];
    RebtFactory.$inject = ['$resource'];

    angular.module("app.services")
        .factory("Product", ProdFactory)
        .factory("Customer", CustFactory)
        .factory("Rebate", RebtFactory);
}(angular));