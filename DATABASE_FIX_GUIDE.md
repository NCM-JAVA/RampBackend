# Database Migration Fix Guide

## Issue Description
When running the application with an existing database, you may encounter this error:
```
Error executing DDL "alter table users add column active boolean not null"
```

This happens because Hibernate tries to add the `active` column to existing user records without a default value.

---

## ✅ Solution Applied

### **1. Fixed Users Entity**
**File:** `src/main/java/com/ramp/entity/Users.java`

**Change:**
```java
// Before (causing error)
@Column(nullable = false)
private Boolean active = true;

// After (fixed)
@Column(nullable = false, columnDefinition = "boolean default true")
private Boolean active = true;
```

**What this does:**
- Adds `DEFAULT true` at database level
- Allows existing records to get default value automatically
- Prevents the "not null constraint" error

---

## 🔧 Migration Options

### **Option 1: Fresh Database (Recommended for Development)**

If you're okay with losing existing data:

```bash
# Connect to PostgreSQL
psql -U postgres -d your_database_name

# Drop and recreate database
DROP DATABASE ramp_db;
CREATE DATABASE ramp_db;

# Exit psql
\q

# Restart application
./mvnw spring-boot:run
```

---

### **Option 2: Manual SQL Migration (Keep Existing Data)**

If you want to keep existing data, run the migration script:

```bash
# Navigate to project directory
cd C:\Users\Admin\MicroserviceProject\Ramp

# Run migration script
psql -U postgres -d your_database_name -f database_migration.sql
```

**Or manually execute:**
```sql
-- Add active column with default
ALTER TABLE users ADD COLUMN active boolean DEFAULT true NOT NULL;

-- Update existing records
UPDATE users SET active = true WHERE active IS NULL;

-- Verify
SELECT COUNT(*), active FROM users GROUP BY active;
```

---

### **Option 3: Update Hibernate to Use update-with-default**

Add to `application.properties`:
```properties
# Allow Hibernate to handle default values
spring.jpa.properties.hibernate.globally_quoted_identifiers=true
spring.jpa.properties.hibernate.globally_quoted_identifiers_skip_column_definitions=true
```

---

## 📝 Check Your Current Database

Before choosing an option, check what's in your database:

```sql
-- Connect to database
psql -U postgres -d your_database_name

-- Check if active column exists
SELECT column_name, data_type, is_nullable, column_default
FROM information_schema.columns
WHERE table_name = 'users' AND column_name = 'active';

-- Check existing users
SELECT user_id, user_name, email, active FROM users;
```

---

## 🚀 Quick Fix Steps

### **If you have NO important data:**
```bash
1. Stop application
2. Drop database: DROP DATABASE ramp_db;
3. Create database: CREATE DATABASE ramp_db;
4. Start application: ./mvnw spring-boot:run
5. ✅ Application creates tables with correct schema
```

### **If you have important data:**
```bash
1. Stop application
2. Run migration script: psql -U postgres -d ramp_db -f database_migration.sql
3. Start application: ./mvnw spring-boot:run
4. ✅ Existing data preserved, column added
```

---

## 🔍 Verify the Fix

After applying the fix, verify:

```sql
-- Check column definition
\d users

-- Should show:
-- active | boolean | not null | default true

-- Check data
SELECT
    COUNT(*) as total_users,
    COUNT(CASE WHEN active = true THEN 1 END) as active_users,
    COUNT(CASE WHEN active = false THEN 1 END) as inactive_users
FROM users;
```

---

## 📋 Troubleshooting

### **Error: Column already exists**
```sql
-- Check if column exists
SELECT * FROM information_schema.columns
WHERE table_name = 'users' AND column_name = 'active';

-- If it exists but has wrong definition, fix it:
ALTER TABLE users ALTER COLUMN active SET DEFAULT true;
ALTER TABLE users ALTER COLUMN active SET NOT NULL;
UPDATE users SET active = true WHERE active IS NULL;
```

### **Error: Cannot add NOT NULL column**
```sql
-- Add column as nullable first
ALTER TABLE users ADD COLUMN active boolean DEFAULT true;

-- Update existing records
UPDATE users SET active = true WHERE active IS NULL;

-- Then add NOT NULL constraint
ALTER TABLE users ALTER COLUMN active SET NOT NULL;
```

### **Error: Permission denied**
Make sure you're connected as a user with ALTER TABLE permissions:
```sql
-- Check permissions
\dp users

-- Grant permissions if needed (as superuser)
GRANT ALL PRIVILEGES ON TABLE users TO your_app_user;
```

---

## 🎯 Prevention for Future

To avoid similar issues in production:

### **1. Use Database Migration Tools**

Add Flyway or Liquibase to `pom.xml`:

```xml
<!-- Flyway for database migrations -->
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```

### **2. Version Control Database Schema**

Create migration files like:
```
src/main/resources/db/migration/
├── V1__Initial_Schema.sql
├── V2__Add_Active_Column.sql
└── V3__Add_Content_Table.sql
```

### **3. Update application.properties**

```properties
# For development - auto-update schema
spring.jpa.hibernate.ddl-auto=update

# For production - validate only, use migrations
spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true
```

---

## 📊 Database Schema After Fix

### **Users Table:**
```sql
CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    user_name VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    full_name VARCHAR(255),
    mobile_number VARCHAR(255),
    pan_number VARCHAR(255),
    enterprise_name VARCHAR(255),
    enterprise_activity VARCHAR(255),
    enterprise_city VARCHAR(255),
    enterprise_address TEXT,
    gst_number VARCHAR(255),
    udyam_uap_number VARCHAR(255),
    business_type VARCHAR(255),
    accept_terms BOOLEAN,
    status VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT true,  -- ✅ Fixed
    created_date TIMESTAMP,
    modified_date TIMESTAMP,
    created_by BIGINT,
    otp VARCHAR(255),
    address TEXT,
    phone_number VARCHAR(255),
    role_id BIGINT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES role_entity(role_id)
);
```

### **Content Table:**
```sql
CREATE TABLE content (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    poster_url VARCHAR(255),
    poster_filename VARCHAR(255),
    status VARCHAR(255) NOT NULL,
    rejection_reason VARCHAR(255),
    created_date TIMESTAMP,
    updated_date TIMESTAMP,
    approved_date TIMESTAMP,
    created_by BIGINT NOT NULL,
    approved_by BIGINT,
    FOREIGN KEY (created_by) REFERENCES users(user_id),
    FOREIGN KEY (approved_by) REFERENCES users(user_id)
);
```

---

## ✅ Summary

**Problem:** Existing database schema doesn't have `active` column with default value.

**Solution Applied:**
1. ✅ Updated `Users.java` entity with `columnDefinition = "boolean default true"`
2. ✅ Created migration script `database_migration.sql`
3. ✅ Code compiled successfully

**Next Steps:**
1. Choose migration option (fresh DB or manual migration)
2. Run migration
3. Start application
4. ✅ Done!

---

## 🆘 Need Help?

If you still encounter issues:

1. Check PostgreSQL logs:
   ```bash
   # Windows
   C:\Program Files\PostgreSQL\{version}\data\log\

   # Linux
   /var/log/postgresql/
   ```

2. Enable Hibernate SQL logging in `application.properties`:
   ```properties
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   logging.level.org.hibernate.SQL=DEBUG
   ```

3. Check database connection:
   ```bash
   psql -U postgres -d ramp_db -c "SELECT version();"
   ```

---

**Database fix completed!** ✅
