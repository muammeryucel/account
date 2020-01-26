rm account.mv.db
rm account.lock.db
curl -X DELETE http://localhost:8024/v1/devmode/purge-events
