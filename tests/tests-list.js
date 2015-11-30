var connection;

QUnit.module("Module 1", function(hooks) {    
    hooks.beforeEach(function(assert) {
        // insert something executed before each test
        connection = new autobahn.Connection({
            url: wstarget,
            realm: wsrealm
        });
        connection.open();
        var leftSeconds = 25;
        while(leftSeconds > 0) {
            setTimeout(function() {
                leftSeconds = (connection.isConnected ? 0 : leftSeconds - 1);
            }, 1000);
        }
        assert.ok(connection.isConnected == true);
    });
    
    QUnit.test( "check something", function( assert ) {
        assert.ok(connection.isConnected == true);
        // do something here that requires open connection...
    }); 
});

