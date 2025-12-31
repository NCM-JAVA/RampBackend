-- Database Migration Script for RAMP CMS
-- Run this script if you have existing users table without 'active' column

-- Step 1: Add active column with default value (if not exists)
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'users' AND column_name = 'active'
    ) THEN
        ALTER TABLE users ADD COLUMN active boolean DEFAULT true NOT NULL;
        RAISE NOTICE 'Column active added successfully';
    ELSE
        RAISE NOTICE 'Column active already exists';
    END IF;
END $$;

-- Step 2: Update existing records to set active = true
UPDATE users SET active = true WHERE active IS NULL;

-- Step 3: Verify the update
SELECT
    COUNT(*) as total_users,
    COUNT(CASE WHEN active = true THEN 1 END) as active_users,
    COUNT(CASE WHEN active = false THEN 1 END) as inactive_users
FROM users;

-- Output: Shows user counts by active status
