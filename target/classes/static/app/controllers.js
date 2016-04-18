(function(angular) {
    var AppController = function($scope, Product, Customer, Rebate) {

        /* Initialization */
        $scope.appliedRebates = Rebate.getAppliedRebate({forall: 'forall'});

        Customer.query(function(response) {
            $scope.customers = response ? response : [];
        });
        Product.query(function(response) {
            $scope.products = response ? response : [];
        });

        /* Onclick functions */
        $scope.getCustDetail = function(custId) {
            $scope.selectedCust = Customer.get({id: custId});
            getCustRebates(custId);
            getProdCustRebates($scope.selectedProd.id, custId);
        };

        $scope.getProdDetail = function(prodId) {
            $scope.selectedProd = Product.get({id: prodId});
            getProdRebates(prodId);
            if ($scope.selectedCust == null) { $scope.rebatePrices = Rebate.getAppliedRebate({price: 'price', prod: 'product',pid: prodId}); }
            else { getProdCustRebates(prodId, $scope.selectedCust.id); }
        };


        /* Functions to retrieve data from server, supported by factory services */
        function getProdCustRebates(prodId, custId) {
            $scope.appliedRebates = Rebate.getAppliedRebate({prod: 'product', cust: 'customer', pid: prodId, cid: custId});
            $scope.rebatePrices = Rebate.getAppliedRebate({price: 'price', prod: 'product', cust: 'customer', pid: prodId, cid: custId});
        };
        function getProdRebates(prodId) {
            $scope.prodRebates = Rebate.getAppliedRebate({prod: 'product', pid: prodId});
            if($scope.selectedCust == null) { $scope.appliedRebates = $scope.prodRebates; }
        };
        function getCustRebates(custId) {
            $scope.custRebates = Rebate.getAppliedRebate({cust: 'customer', cid: custId});
        };
    };

    AppController.$inject = ['$scope', 'Product', 'Customer', 'Rebate'];
    angular.module("app.controllers")
        .controller("AppController", AppController);
}(angular));