# RAMP CMS - Setup Guide

## Overview
Complete Content Management System with Role-Based Access Control (RBAC) and JWT Authentication.

---

## 1. Swagger API Documentation Setup

### Access Swagger UI
After starting the application, access Swagger at:
```
http://localhost:8080/swagger-ui.html
```

### Using JWT Authentication in Swagger

**Step 1: Login**
1. Expand the **Authentication** section
2. Click on `POST /api/auth/login`
3. Click **Try it out**
4. Use default credentials:
```json
{
  "userName": "superadmin",
  "password": "admin123"
}
```
5. Click **Execute**
6. Copy the `token` value from the response

**Step 2: Authorize**
1. Click the **Authorize** button (lock icon) at the top of Swagger UI
2. In the popup, enter: `Bearer <your-copied-token>`
   - Example: `Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`
3. Click **Authorize**
4. Click **Close**

**Step 3: Make Authenticated Requests**
- All subsequent API requests will automatically include the JWT token
- The lock icon will show as locked (🔒) for protected endpoints
- Token remains valid until logout or expiration

### Swagger Features
- **Comprehensive API Documentation**: All endpoints documented with examples
- **Try It Out**: Test APIs directly from browser
- **Request/Response Schemas**: View data models
- **Role-Based Filtering**: See which endpoints require which roles
- **Authentication Status**: Visual indicators for protected endpoints

---

## 2. Postman Collection Setup

### Import Postman Collection

**Option 1: Import from File**
1. Open Postman
2. Click **Import** button
3. Select the file: `RAMP_CMS_Postman_Collection.json`
4. Click **Import**

**Option 2: Import from Raw JSON**
1. Open Postman
2. Click **Import** → **Raw text**
3. Copy and paste the entire JSON content
4. Click **Continue** → **Import**

### Configure Environment Variables

The collection includes two variables:
1. **baseUrl**: `http://localhost:8080` (default)
2. **jwtToken**: Auto-populated after login

**To Change Base URL:**
1. Click on the collection name "RAMP CMS - Complete API Collection"
2. Go to **Variables** tab
3. Update `baseUrl` current value
4. Click **Save**

### Using the Postman Collection

#### Automatic Token Management
The collection includes automatic token management:
- Login requests automatically save the JWT token
- Token is automatically used in all subsequent requests
- No manual token copying required!

#### Quick Start Flow

**1. Login (Choose One):**
- **Super Admin Login**: Uses default credentials (superadmin/admin123)
- **Custom User Login**: Enter your credentials

**2. After Login:**
- Token is automatically saved to `{{jwtToken}}` variable
- All protected endpoints will use this token automatically

**3. Test APIs:**
- Navigate through folders: Super Admin, Admin, Content Manager
- Click **Send** on any request
- Token is automatically included in Authorization header

### Collection Structure

```
📁 RAMP CMS - Complete API Collection
├── 📁 Authentication
│   ├── Login - Super Admin (auto-saves token)
│   ├── Login - Custom User (auto-saves token)
│   ├── Register Entrepreneur
│   └── Register Content Manager (Public)
├── 📁 Super Admin (Requires: SUPER_ADMIN token)
│   ├── Register Admin User
│   ├── Register Content Manager
│   ├── Get All Users
│   ├── Get Users by Role - ADMIN
│   └── Get Users by Role - CONTENT_MANAGER
├── 📁 Admin (Requires: ADMIN or SUPER_ADMIN token)
│   ├── Get Dashboard Statistics
│   ├── Get Pending Content
│   ├── Get All Content
│   ├── Approve Content
│   └── Reject Content
├── 📁 Content Manager (Requires: CONTENT_MANAGER token)
│   ├── Create Content
│   ├── Get My Content
│   ├── Update Content
│   ├── Delete Content
│   └── Get Content by ID
└── 📁 Public APIs (No authentication)
    └── Get Approved Content (Public)
```

---

## 3. Testing Workflow Examples

### Scenario 1: Super Admin Creates Users

```
1. Authentication → Login - Super Admin → Send
   ✓ Token saved automatically

2. Super Admin → Register Admin User → Send
   ✓ Creates admin: johnadmin / Admin@123

3. Super Admin → Register Content Manager → Send
   ✓ Creates content manager: sarahmanager / Manager@123

4. Super Admin → Get All Users → Send
   ✓ View all registered users
```

### Scenario 2: Content Manager Workflow

```
1. Authentication → Login - Custom User → Send
   - Username: sarahmanager
   - Password: Manager@123
   ✓ Token saved automatically

2. Content Manager → Create Content → Send
   ✓ Content created with status: PENDING

3. Content Manager → Get My Content → Send
   ✓ View all my content with statuses

4. (Wait for admin approval)

5. Content Manager → Update Content → Send (if rejected)
   ✓ Resubmit updated content
```

### Scenario 3: Admin Approval Workflow

```
1. Authentication → Login - Custom User → Send
   - Username: johnadmin
   - Password: Admin@123
   ✓ Token saved automatically

2. Admin → Get Dashboard Statistics → Send
   ✓ View content counts

3. Admin → Get Pending Content → Send
   ✓ View all pending content

4a. Admin → Approve Content → Send
    - Update contentId in URL: /approve/{id}
    ✓ Content approved, now live

4b. Admin → Reject Content → Send
    - Provide contentId and rejectionReason
    ✓ Content rejected with reason
```

### Scenario 4: Public Website Integration

```
1. Public APIs → Get Approved Content (Public) → Send
   ✓ No authentication required
   ✓ Returns all approved content for website display
```

---

## 4. API Response Examples

### Successful Login Response
```json
{
  "statusCode": 200,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzdXBlcmFkbWluIiwiaWF0IjoxNzA5MjA4MDAwfQ.xyz",
    "user": {
      "loginUsername": "superadmin",
      "authorizedEmail": "superadmin@cms.com",
      "role": "SUPER_ADMIN"
    }
  },
  "message": "Login successful"
}
```

### Content Creation Response
```json
{
  "statusCode": 201,
  "data": {
    "id": 1,
    "title": "Summer Sale 2025",
    "description": "Get up to 50% off on all products!",
    "posterUrl": "https://cdn.example.com/posters/summer-sale-2025.jpg",
    "posterFilename": "summer-sale-2025.jpg",
    "status": "PENDING",
    "createdByUsername": "sarahmanager",
    "createdByName": "Sarah Content Manager",
    "createdDate": "2025-10-13T01:00:00"
  },
  "message": "Content created successfully and submitted for approval"
}
```

### Dashboard Statistics Response
```json
{
  "statusCode": 200,
  "data": {
    "totalContent": 15,
    "pendingContent": 3,
    "approvedContent": 10,
    "rejectedContent": 2
  },
  "message": "Dashboard stats retrieved successfully"
}
```

---

## 5. Authentication & Authorization

### JWT Token Structure
- **Header**: Authorization: Bearer <token>
- **Format**: `Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`
- **Expiration**: Configured in application properties
- **Claims**: Contains username and authorities

### Role Hierarchy
```
SUPER_ADMIN (Full Access)
    ├── Can do everything Admin can do
    ├── Can create Admin users
    ├── Can create Content Manager users
    └── Can view all users

ADMIN (Content Management)
    ├── Can view all content
    ├── Can approve/reject content
    └── Can view dashboard statistics

CONTENT_MANAGER (Content Creation)
    ├── Can create content
    ├── Can view own content
    ├── Can update pending/rejected content
    └── Can delete own content
```

### Endpoint Protection
- **Public**: `/api/auth/**`, `/api/content/public/**`
- **Super Admin Only**: `/api/superadmin/**`
- **Admin + Super Admin**: `/api/admin/**`
- **Content Manager + Admin + Super Admin**: `/api/content/**`

---

## 6. Troubleshooting

### Issue: 401 Unauthorized
**Solution:**
1. Verify token is saved: Check Postman variable `{{jwtToken}}`
2. Re-login to get fresh token
3. Ensure token format is: `Bearer <token>` (not just `<token>`)

### Issue: 403 Forbidden
**Solution:**
1. Check if your user has the required role
2. Super Admin endpoints require SUPER_ADMIN role
3. Admin endpoints require ADMIN or SUPER_ADMIN role

### Issue: Token Not Auto-Saving in Postman
**Solution:**
1. Check the **Tests** tab in login requests
2. Verify the test script is present
3. Check Postman console for errors

### Issue: Swagger Authorization Not Working
**Solution:**
1. Ensure you included "Bearer " prefix
2. Token should be fresh (not expired)
3. Click the lock icon to verify authorization status

---

## 7. Default Credentials

```
Role: SUPER_ADMIN
Username: superadmin
Password: admin123
Email: superadmin@cms.com
```

**⚠️ IMPORTANT:** Change the default password in production!

---

## 8. Quick Reference

### Base URL
```
Local: http://localhost:8080
Swagger: http://localhost:8080/swagger-ui.html
```

### Content Statuses
- **PENDING**: Awaiting admin review
- **APPROVED**: Live on website
- **REJECTED**: Rejected with reason

### Roles
- **SUPER_ADMIN**: System administrator
- **ADMIN**: Content approver
- **CONTENT_MANAGER**: Content creator
- **Entrepreneur**: Business user

---

## 9. Best Practices

1. **Always login first** before testing protected endpoints
2. **Use environment variables** for baseUrl in Postman
3. **Check response status codes** for error details
4. **Use Swagger** for quick API exploration
5. **Use Postman** for automated testing workflows
6. **Keep tokens secure** - never share or commit them
7. **Test role-based access** by logging in as different users

---

## 10. Next Steps

1. ✅ Import Postman collection
2. ✅ Login as Super Admin
3. ✅ Create test Admin user
4. ✅ Create test Content Manager user
5. ✅ Test content creation workflow
6. ✅ Test approval workflow
7. ✅ Test public API for website integration

---

## Support

For issues or questions:
1. Check Swagger documentation: `http://localhost:8080/swagger-ui.html`
2. Review API response error messages
3. Check application logs
4. Verify database connections

---

**Happy Testing! 🚀**
