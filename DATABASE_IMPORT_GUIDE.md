# Database Import Guide - Logistics API

## Overview
The `init.sql` file is a complete PostgreSQL database dump that can be imported into any PostgreSQL installation. This allows other users to quickly set up the database with all tables and sample data.

---

## What's in init.sql?

✅ **2 Tables**:
- `orders` - 7 sample records
- `vehicles` - 3 sample records

✅ **6 Indexes** - For optimized queries

✅ **Complete Data** - Ready to use immediately

---

## Import Methods

### Method 1: Command Line (Linux/Mac/Windows)

#### Prerequisites
- PostgreSQL installed
- Database user created
- Database `myappdb` created

#### Steps

**1. Create database (if not exists):**
```bash
psql -U postgres -c "CREATE DATABASE myappdb;"
```

**2. Import the SQL file:**
```bash
psql -U myappuser -d myappdb -f init.sql
```

**Parameters explained:**
- `-U myappuser` - PostgreSQL username
- `-d myappdb` - Database name
- `-f init.sql` - File path to import

**3. Verify import:**
```bash
psql -U myappuser -d myappdb -c "SELECT COUNT(*) FROM orders;"
```

Expected output: `count = 7`

---

### Method 2: pgAdmin GUI (Windows/Linux/Mac)

#### Prerequisites
- pgAdmin installed and running
- PostgreSQL server connection configured

#### Steps

**1. Create database:**
- Right-click "Databases"
- Select "Create > Database"
- Name: `myappdb`
- Click "Save"

**2. Open Query Tool:**
- Right-click on `myappdb`
- Select "Query Tool"

**3. Open SQL file:**
- Click "Open File" icon
- Select `init.sql`
- SQL appears in editor

**4. Execute:**
- Click "Execute" button (or F5)
- Wait for completion

**5. Verify:**
```sql
SELECT COUNT(*) FROM orders;
SELECT COUNT(*) FROM vehicles;
```

---

### Method 3: Using PostgreSQL CLI directly

```bash
# Open psql prompt
psql -U myappuser -d myappdb

# Inside psql, execute file
\i init.sql

# Verify
\dt                      # List tables
SELECT COUNT(*) FROM orders;
SELECT COUNT(*) FROM vehicles;

# Exit
\q
```

---

### Method 4: Docker PostgreSQL

If running PostgreSQL in Docker:

```bash
# Docker Compose will automatically run init.sql on first startup
docker-compose up -d

# Verify (wait a few seconds for initialization)
docker-compose exec postgres psql -U myappuser -d myappdb -c "SELECT COUNT(*) FROM orders;"
```

---

## Complete Setup Example

### For a New User

**Prerequisites:**
- PostgreSQL 16+ installed
- Port 5432 available

**Steps:**

```bash
# 1. Start PostgreSQL (Windows)
net start postgresql-x64-16

# 2. Create database
psql -U postgres -c "CREATE USER myappuser WITH PASSWORD 'myapppassword';"
psql -U postgres -c "CREATE DATABASE myappdb OWNER myappuser;"
psql -U postgres -c "ALTER USER myappuser CREATEDB;"

# 3. Import the database
psql -U myappuser -d myappdb -f init.sql

# 4. Verify (you should see 7 orders)
psql -U myappuser -d myappdb -c "SELECT COUNT(*) FROM orders;"

# 5. Connect and explore
psql -U myappuser -d myappdb
```

**Inside psql:**
```sql
-- View all orders
SELECT * FROM orders;

-- View all vehicles
SELECT * FROM vehicles;

-- See summary
SELECT COUNT(*) as total_orders FROM orders;
SELECT COUNT(*) as total_vehicles FROM vehicles;

-- Exit
\q
```

---

## Verify Import Success

After importing, run these commands to verify:

```bash
psql -U myappuser -d myappdb
```

Then in psql prompt:

```sql
-- Check tables exist
\dt

-- Check record counts
SELECT 'Orders' as table_name, COUNT(*) as count FROM orders
UNION ALL
SELECT 'Vehicles' as table_name, COUNT(*) as count FROM vehicles;

-- Check sample orders
SELECT id, customer_name, weight, shipping_type, status, cost FROM orders LIMIT 5;

-- Check sample vehicles
SELECT id, type, licenseplate, capacity, maxspeed, status FROM vehicles LIMIT 3;

-- Check indexes
\d orders
\d vehicles
```

---

## File Location & Distribution

### Share with Others:
1. **Email**: Attach `init.sql` directly
2. **Git**: Commit to repository
3. **Cloud Storage**: Upload to OneDrive, Google Drive, etc.
4. **ZIP Archive**: Package with documentation
5. **Docker Compose**: Use docker-compose.yml to auto-initialize

### Directory Structure:
```
Logistics_API/
├── init.sql                      ← Database dump file
├── docker-compose.yml            ← Auto-initialization config
├── DOCUMENTATION.md              ← Project docs
├── DOCKER_GUIDE.md              ← Docker setup
├── API_TESTS.http               ← API test cases
├── DATABASE_IMPORT_GUIDE.md     ← This file
└── ...
```

---

## Troubleshooting

### Issue: "Database already exists"
```bash
# Drop and recreate
psql -U postgres -c "DROP DATABASE myappdb;"
psql -U postgres -c "CREATE DATABASE myappdb;"
psql -U myappuser -d myappdb -f init.sql
```

### Issue: "Permission denied" / "User does not exist"
```bash
# Create user first
psql -U postgres -c "CREATE USER myappuser WITH PASSWORD 'myapppassword';"

# Give permissions
psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE myappdb TO myappuser;"
```

### Issue: "Could not open file"
```bash
# Check file location
pwd
ls -la init.sql

# Use full path if needed
psql -U myappuser -d myappdb -f /full/path/to/init.sql
```

### Issue: "Role does not have CONNECT privilege"
```bash
psql -U postgres -c "GRANT CONNECT ON DATABASE myappdb TO myappuser;"
```

### Issue: Table already exists
The `init.sql` includes `DROP TABLE IF EXISTS`, so it's safe to run multiple times. Old data will be replaced.

### Issue: Docker container init.sql not running
```bash
# Check Docker logs
docker-compose logs postgres | grep init.sql

# Verify file location
ls -la init.sql

# Make sure init.sql is in project root (same directory as docker-compose.yml)
```

---

## Data Included

### Orders (7 records)
| ID | Customer | Weight | Destination | Type | Status | Cost |
|----|----------|--------|-------------|------|--------|------|
| 18 | C1 | 10 | Italy | STANDARD | PENDING | 25 |
| 21 | C1 | 10 | Italy | STANDARD | PENDING | 25 |
| 22 | C1 | 10 | Italy | STANDARD | PENDING | 0 |
| 20 | C3 | 3 | Italy | STANDARD | READY | 7.5 |
| 19 | C2 | 7 | Italy | STANDARD | DELAYED | 17.5 |
| 24 | C5 | 3 | Africa | EXPRESS | READY | 11.25 |
| 23 | C5 | 3 | France | STANDARD | DELAYED | 7.5 |

### Vehicles (3 records)
| ID | Type | License Plate | Capacity | Max Speed | Status |
|----|------|---------------|----------|-----------|--------|
| 1 | TRUCK | ABC123 | 1000 | 120 | available |
| 2 | VAN | XYZ789 | 500 | 100 | in_service |
| 3 | DRONE | LMN456 | 200 | 80 | available |

---

## FAQ

**Q: Can I modify the data in init.sql?**
A: Yes! Edit `init.sql` before importing to change sample data.

**Q: Will importing delete existing data?**
A: Yes, `DROP TABLE IF EXISTS` will delete old tables. Backup first if needed.

**Q: Can I use this on different PostgreSQL versions?**
A: Yes, the SQL is standard and works on PostgreSQL 12+.

**Q: Do I need to modify passwords?**
A: Replace `myapppassword` and `myappuser` if using different credentials.

**Q: Can multiple users use the same database?**
A: Yes, but they need their own login credentials.

**Q: Does Docker automatically run init.sql?**
A: Yes! When using docker-compose.yml, init.sql is automatically executed on first startup. The init.sql file must be in the project root directory.

---

## Quick Command Reference

```bash
# Import database manually
psql -U myappuser -d myappdb -f init.sql

# Verify import
psql -U myappuser -d myappdb -c "SELECT COUNT(*) FROM orders;"

# Connect interactively
psql -U myappuser -d myappdb

# List all databases
psql -U postgres -l

# Drop database (if needed)
psql -U postgres -c "DROP DATABASE myappdb;"

# Backup database (export)
pg_dump -U myappuser -d myappdb > backup.sql

# Restore from backup
psql -U myappuser -d myappdb -f backup.sql

# Docker: Auto-initialize on startup
docker-compose up -d

# Docker: Check initialization logs
docker-compose logs postgres
```

---

## Summary

| Aspect | Details |
|--------|---------|
| **File Name** | `init.sql` |
| **File Size** | < 5 KB |
| **Tables** | 2 (orders, vehicles) |
| **Records** | 10 total (7 orders + 3 vehicles) |
| **Indexes** | 6 (for performance) |
| **Time to Import** | < 1 second |
| **PostgreSQL Version** | 12+ compatible |
| **Docker Compose** | ✅ Supported (auto-init on startup) |

Now any user can easily import the complete Logistics API database! 🚀

---

**Last Updated**: March 18, 2026
**Version**: 2.0 (Updated with actual data)
