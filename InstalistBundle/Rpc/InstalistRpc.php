<?php

namespace InstalistBundle\Rpc;

use Ratchet\ConnectionInterface;
use Gos\Bundle\WebSocketBundle\RPC\RpcInterface;
use Gos\Bundle\WebSocketBundle\Router\WampRequest;

class InstalistRpc implements RpcInterface
{
    /**
     * Adds the params together
     *
     * Note: $conn isnt used here, but contains the connection of the person making this request.
     *
     * @param ConnectionInterface $connection
     * @param WampRequest $request
     * @param array $params
     * @return int
     */
    public function sum(ConnectionInterface $connection, WampRequest $request, $params)
    {
        return array("result" => array_sum($params), implode($params));
    }

    public function testTest(ConnectionInterface $connection, WampRequest $request, $params)
    {
        return array("result" => array_sum($params), implode($params));
    }

    public function sample(ConnectionInterface $connection, WampRequest $request, $params){

        return implode($params);
    }

    /**
     * Name of RPC, use for pubsub router (see step3)
     *
     * @return string
     */
    public function getName()
    {
        return 'instalist.rpc';
    }
}
