var connection;

QUnit.module("Module 1", function (hooks) {
    var session = null;

    hooks.beforeEach(function (assert) {

        var that = this;
        var done1 = assert.async(1);
        console.log("Set up");
        ab.connect(
            // The WebSocket URI of the WAMP server
            wstarget,

            // The onconnect handler
            sessionEstablished,
            // hanup handler when somehting bad happens
            hangUpHandler,
            // The session options
            {
                'maxRetries': 0,
                'retryDelay': 0
            }
        );

        function sessionEstablished(_session) {
            console.log("Test setup session: ");
            console.log(_session);
            session = _session;
            done1();
        }

        function hangUpHandler(code, reason, detail) {
            // WAMP session closed here ..
            console.log(code + " " + reason);
            console.log(detail);
            done1();
        }

    });

    hooks.afterEach(function (assert){

    });

    QUnit.test("RPC Call sum test!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection...
        // WAMP session established here ..
        session.call("instalist/sum", {"term1": 1, "term2": 2}).then(function (result) {
            console.log("result");
            assert.ok(3, result.result);
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            console.error("Error in rpc!" + error);
            assert.ok(false);
            done();
        });
    });

    QUnit.test("RPC2 Call sum test!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection...
        // WAMP session established here ..
        session.call("instalist/sum", {"term1": 1, "term2": 2}).then(function (result) {
            assert.ok(3, result.result);
            console.log("result");
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            console.error("Error in rpc!" + error);
            assert.ok(false);
            done();
        });
    });

});

