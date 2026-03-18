# PostgreSQL Auto-Initialization with Docker

## Overview
When PostgreSQL boots in Docker, it automatically executes all SQL files in `/docker-entrypoint-initdb.d/` directory. The `init.sql` file is mounted there, so the database is automatically initialized with tables and sample data.

---

## How It Works

### Docker-Compose Configuration
```yaml
volumes:
  - ./init.sql:/docker-entrypoint-initdb.d/init.sql
```

**Explanation:**
- `./init.sql` - The file on your local machine (project root)
- `/docker-entrypoint-initdb.d/init.sql` - Location inside PostgreSQL container
- PostgreSQL automatically runs all `.sql` files in this directory during startup

---

## Initialization Process

When you run `docker-compose up -d`:

```
1. Docker starts PostgreSQL container
2. PostgreSQL creates the database 'myappdb'
3. PostgreSQL creates the user 'myappuser'
4. PostgreSQL checks /docker-entrypoint-initdb.d/ directory
5. PostgreSQL finds init.sql
6. PostgreSQL executes init.sql automatically
7. Tables are created (orders, vehicles)
8. Sample data is inserted (10 orders + 8 vehicles)
9. PostgreSQL is ready to use!
```

---

## Quick Start

### 1. Verify file structure
```bash
# Make sure init.sql is in project root
ls -la init.sql
```

**Expected output:**
```
-rw-r--r--  1 user  group  3934 Mar 18 10:00 init.sql
```

### 2. Build and start
```bash
# Navigate to project
cd Logistics_API

# Build images
docker-compose build

# Start services
docker-compose up -d
```

### 3. Wait for initialization (about 5-10 seconds)
```bash
# Check PostgreSQL logs
docker-compose logs postgres
```

**Look for:**
```
postgres_db  | executing /docker-entrypoint-initdb.d/init.sql
postgres_db  | CREATE TABLE
postgres_db  | INSERT 0 10
postgres_db  | INSERT 0 8
```

### 4. Verify initialization succeeded
```bash
# Check record count
docker-compose exec postgres psql -U myappuser -d myappdb -c "SELECT COUNT(*) FROM orders;"
```

**Expected output:**
```
 count
-------
    10
(1 row)
```

---

## What Gets Created Automatically

### Tables
✅ `orders` table with 10 sample records
✅ `vehicles` table with 8 sample records

### Indexes (for performance)
✅ `idx_orders_status`
✅ `idx_orders_destination`
✅ `idx_orders_shipping_type`
✅ `idx_vehicles_type`
✅ `idx_vehicles_status`
✅ `idx_vehicles_license_plate`

### Sample Data
```
Orders: 10 records
  - PENDING: 5
  - READY: 2
  - DELAYED: 1
  - FAILED: 1

Vehicles: 8 records
  - TRUCK: 3
  - VAN: 3
  - DRONE: 2
```

---

## Access Database After Boot

### Via pgAdmin (GUI)
```
URL: http://localhost:5050
Email: admin@local.com
Password: admin

Add PostgreSQL Server:
- Host: postgres
- Port: 5432
- Username: myappuser
- Password: myapppassword
- Database: myappdb
```

### Via Command Line
```bash
# Connect to database
docker-compose exec postgres psql -U myappuser -d myappdb

# Inside psql:
SELECT * FROM orders;
SELECT * FROM vehicles;
\q  # Exit
```

### Via Docker Desktop
1. Open Docker Desktop
2. Click "postgres_db" container
3. Go to "Exec" tab
4. Run: `psql -U myappuser -d myappdb`

---

## Important Notes

### Data Persistence
- **Volume**: `postgres_data:/var/lib/postgresql/data`
- Data persists even if container stops
- **Important**: `docker-compose down -v` deletes the volume AND data

### Initialization Only on First Run
**Initialization happens only when PostgreSQL container is created fresh:**

```bash
# First time (init.sql runs)
docker-compose up -d

# Stop and restart (init.sql does NOT run again)
docker-compose stop
docker-compose start

# Data is preserved from previous run
```

### Reset Everything (Delete All Data)
```bash
# Stop and remove containers AND volumes
docker-compose down -v

# Rebuild and start fresh (init.sql runs again)
docker-compose build
docker-compose up -d
```

---

## Verify Initialization

### Check Logs
```bash
# View PostgreSQL startup logs
docker-compose logs postgres

# Follow logs in real-time
docker-compose logs -f postgres
```

### Run Verification Queries
```bash
# Count records in each table
docker-compose exec postgres psql -U myappuser -d myappdb -c "
SELECT 'orders' as table_name, COUNT(*) as count FROM orders
UNION ALL
SELECT 'vehicles' as table_name, COUNT(*) as count FROM vehicles;
"
```

**Expected output:**
```
 table_name | count
------------+-------
 orders     |    10
 vehicles   |     8
(2 rows)
```

### List Tables
```bash
docker-compose exec postgres psql -U myappuser -d myappdb -c "\dt"
```

**Expected output:**
```
          List of relations
 Schema |   Name   | Type  |  Owner
--------+----------+-------+----------
 public | orders   | table | myappuser
 public | vehicles | table | myappuser
(2 rows)
```

---

## Troubleshooting

### Issue: init.sql not found
**Error**: "No such file or directory"

**Solution:**
1. Check file location: `ls -la init.sql`
2. Ensure init.sql is in project root (same directory as docker-compose.yml)
3. Path must be relative: `./init.sql`

### Issue: Database not initialized
**Solution:**
1. Check logs: `docker-compose logs postgres`
2. Look for errors in SQL execution
3. Verify init.sql syntax is correct
4. Delete volume and retry: `docker-compose down -v && docker-compose up -d`

### Issue: Tables don't exist
**Solution:**
```bash
# Check if database was created
docker-compose exec postgres psql -U postgres -l

# Check if init.sql was executed
docker-compose logs postgres | grep "init.sql"

# Manually run init.sql
docker-compose exec postgres psql -U myappuser -d myappdb -f /docker-entrypoint-initdb.d/init.sql
```

### Issue: Permission denied when starting
**Solution (Linux/Mac):**
```bash
# Give Docker permission to read file
chmod 644 init.sql

# Start again
docker-compose up -d
```

---

## File Locations Reference

| Item | Location |
|------|----------|
| **Your Machine** | `./init.sql` (project root) |
| **Inside Container** | `/docker-entrypoint-initdb.d/init.sql` |
| **PostgreSQL Data** | `/var/lib/postgresql/data` (inside container) |
| **Docker Volume** | `postgres_data` (persistent storage) |

---

## Complete Workflow Example

```bash
# 1. Navigate to project
cd Logistics_API

# 2. Verify init.sql exists
ls -la init.sql

# 3. Build images
docker-compose build

# 4. Start services (init.sql runs automatically)
docker-compose up -d

# 5. Wait a few seconds for initialization
sleep 5

# 6. Check logs
docker-compose logs postgres | head -20

# 7. Verify data was loaded
docker-compose exec postgres psql -U myappuser -d myappdb -c "SELECT COUNT(*) FROM orders;"

# 8. Connect to database
docker-compose exec postgres psql -U myappuser -d myappdb

# Inside psql:
# SELECT * FROM orders LIMIT 5;
# \q
```

---

## Summary

| Feature | Details |
|---------|---------|
| **Initialization** | Automatic on first startup |
| **Mount Point** | `/docker-entrypoint-initdb.d/` |
| **Execution** | All `.sql` files run alphabetically |
| **Data Persistence** | Via `postgres_data` volume |
| **Time to Initialize** | < 5 seconds |
| **Reset Data** | `docker-compose down -v` |
| **Re-initialize** | Delete volume and restart |

Now PostgreSQL automatically initializes with all tables and sample data when it boots! 🚀

---

**Last Updated**: March 18, 2026
**Version**: 1.0
