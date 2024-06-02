set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE gamerly;
    CREATE USER docker WITH ENCRYPTED PASSWORD 'admin';
    GRANT ALL PRIVILEGES ON DATABASE gamerly TO proyecto;
EOSQL