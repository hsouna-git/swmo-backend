# swmo-backend

## Liens utiles
- Backend : https://swmo-backend-production.up.railway.app/
- Keycloak : https://keycloak-production-5d0e.up.railway.app/
- Frontend : https://swmo-frontend-production.up.railway.app/

## Healthcheck
- Endpoint de santé : /api/health
- Endpoint de statut : /api/status
- Le service pingue automatiquement le backend et l’URL Keycloak avec un délai de 30s après 5s d’attente.

## Swagger
- Swagger UI : /swagger-ui.html
- OpenAPI docs : /v3/api-docs

## Endpoint de logs
- Logs des commandes : /api/orders/logs

## Configuration Keycloak
- Realm : swmo
- Identifiants administrateur : admin / admin
- Utilisateur applicatif : user / user