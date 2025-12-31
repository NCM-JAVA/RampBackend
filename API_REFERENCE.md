# RAMP CMS - Complete API Reference

## 📚 Table of Contents
1. [Authentication APIs](#authentication-apis)
2. [Super Admin APIs](#super-admin-apis)
3. [Admin APIs](#admin-apis)
4. [Content Manager APIs](#content-manager-apis)
5. [Public APIs](#public-apis)
6. [Response Status Codes](#response-status-codes)

---

## Authentication APIs

### 1. Login
**Endpoint:** `POST /api/auth/login`
**Access:** Public (No authentication required)
**Description:** Authenticate user and get JWT token

**Request Body:**
```json
{
  "userName": "superadmin",
  "password": "admin123"
}
```

**Success Response (200):**
```json
{
  "statusCode": 200,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "user": {
      "loginUsername": "superadmin",
      "authorizedEmail": "superadmin@cms.com",
      "authorizedPerson": "Super Administrator",
      "role": "SUPER_ADMIN"
    }
  },
  "message": "Login successful"
}
```

**Error Response (401):**
```json
{
  "statusCode": 401,
  "message": "Invalid credentials"
}
```

---

### 2. Register Entrepreneur
**Endpoint:** `POST /api/auth/register`
**Access:** Public
**Description:** Register a new entrepreneur user

**Request Body:**
```json
{
  "enterpriseName": "Tech Innovations Pvt Ltd",
  "authorizedPerson": "Rajesh Kumar",
  "authorizedEmail": "rajesh@techinnovations.com",
  "authorizedPAN": "ABCDE1234F",
  "authorizedMobile": "9876543210",
  "enterpriseActivity": "Software Development",
  "enterpriseCity": "Bangalore",
  "enterpriseAddress": "123 MG Road, Bangalore",
  "loginUsername": "rajeshkumar",
  "gstNumber": "29ABCDE1234F1Z5",
  "udyamUapNumber": "UDYAM-KA-01-1234567",
  "businessType": "Private Limited",
  "role": "Entrepreneur",
  "acceptTerms": true,
  "password": "Rajesh@123"
}
```

**Success Response (201):**
```json
{
  "statusCode": 201,
  "data": {
    "loginUsername": "rajeshkumar",
    "authorizedEmail": "rajesh@techinnovations.com",
    "role": "Entrepreneur"
  },
  "message": "User registered successfully"
}
```

---

### 3. Register Content Manager (Public)
**Endpoint:** `POST /api/auth/register-cms`
**Access:** Public
**Description:** Self-registration for Content Managers

**Request Body:**
```json
{
  "fullName": "Jane Manager",
  "email": "jane@cms.com",
  "mobileNumber": "9876543211",
  "username": "janemanager",
  "password": "Manager@123",
  "acceptTerms": true
}
```

**Success Response (201):**
```json
{
  "statusCode": 201,
  "data": {
    "loginUsername": "janemanager",
    "authorizedEmail": "jane@cms.com",
    "role": "CONTENT_MANAGER"
  },
  "message": "CMS user registered successfully"
}
```

---

## Super Admin APIs

**Authorization Required:** `Bearer <JWT_TOKEN>`
**Required Role:** `SUPER_ADMIN`

### 1. Register Admin User
**Endpoint:** `POST /api/superadmin/register-admin`
**Description:** Create a new admin user

**Request Body:**
```json
{
  "fullName": "John Admin",
  "email": "john.admin@cms.com",
  "mobileNumber": "9876543210",
  "username": "johnadmin",
  "password": "Admin@123"
}
```

**Success Response (201):**
```json
{
  "statusCode": 201,
  "data": {
    "loginUsername": "johnadmin",
    "authorizedEmail": "john.admin@cms.com",
    "role": "ADMIN"
  },
  "message": "Admin user registered successfully"
}
```

---

### 2. Register Content Manager
**Endpoint:** `POST /api/superadmin/register-content-manager`
**Description:** Create a new content manager user

**Request Body:**
```json
{
  "fullName": "Sarah Manager",
  "email": "sarah@cms.com",
  "mobileNumber": "9876543212",
  "username": "sarahmanager",
  "password": "Manager@123",
  "acceptTerms": true
}
```

**Success Response (201):**
```json
{
  "statusCode": 201,
  "data": {
    "loginUsername": "sarahmanager",
    "authorizedEmail": "sarah@cms.com",
    "role": "CONTENT_MANAGER"
  },
  "message": "CMS user registered successfully"
}
```

---

### 3. Get All Users
**Endpoint:** `GET /api/superadmin/users`
**Description:** Retrieve all users in the system

**Success Response (200):**
```json
{
  "statusCode": 200,
  "data": [
    {
      "loginUsername": "superadmin",
      "authorizedEmail": "superadmin@cms.com",
      "authorizedPerson": "Super Administrator",
      "role": "SUPER_ADMIN"
    },
    {
      "loginUsername": "johnadmin",
      "authorizedEmail": "john.admin@cms.com",
      "authorizedPerson": "John Admin",
      "role": "ADMIN"
    }
  ],
  "message": "Users retrieved successfully"
}
```

---

### 4. Get Users by Role
**Endpoint:** `GET /api/superadmin/users/role/{role}`
**Description:** Get all users with specific role
**Path Parameters:**
- `role`: ADMIN | CONTENT_MANAGER | Entrepreneur | SUPER_ADMIN

**Example:** `GET /api/superadmin/users/role/ADMIN`

**Success Response (200):**
```json
{
  "statusCode": 200,
  "data": [
    {
      "loginUsername": "johnadmin",
      "authorizedEmail": "john.admin@cms.com",
      "role": "ADMIN"
    }
  ],
  "message": "Users retrieved successfully"
}
```

---

## Admin APIs

**Authorization Required:** `Bearer <JWT_TOKEN>`
**Required Role:** `ADMIN` or `SUPER_ADMIN`

### 1. Get Dashboard Statistics
**Endpoint:** `GET /api/admin/dashboard/stats`
**Description:** Get content statistics overview

**Success Response (200):**
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

### 2. Get Pending Content
**Endpoint:** `GET /api/admin/content/pending`
**Description:** Get all content awaiting approval

**Success Response (200):**
```json
{
  "statusCode": 200,
  "data": [
    {
      "id": 1,
      "title": "Summer Sale 2025",
      "description": "Get up to 50% off on all products!",
      "posterUrl": "https://cdn.example.com/posters/summer-sale.jpg",
      "posterFilename": "summer-sale.jpg",
      "status": "PENDING",
      "createdByUsername": "sarahmanager",
      "createdByName": "Sarah Manager",
      "createdDate": "2025-10-13T01:00:00"
    }
  ],
  "message": "Pending content retrieved successfully"
}
```

---

### 3. Get All Content
**Endpoint:** `GET /api/admin/content/all`
**Description:** Get all content (pending, approved, rejected)

**Success Response (200):**
```json
{
  "statusCode": 200,
  "data": [
    {
      "id": 1,
      "title": "Summer Sale 2025",
      "status": "APPROVED",
      "approvedByUsername": "johnadmin",
      "approvedDate": "2025-10-13T02:00:00"
    },
    {
      "id": 2,
      "title": "Winter Collection",
      "status": "PENDING"
    },
    {
      "id": 3,
      "title": "Old Campaign",
      "status": "REJECTED",
      "rejectionReason": "Images not clear"
    }
  ],
  "message": "All content retrieved successfully"
}
```

---

### 4. Approve Content
**Endpoint:** `POST /api/admin/content/approve/{contentId}`
**Description:** Approve pending content (makes it live)
**Path Parameters:**
- `contentId`: ID of content to approve

**Example:** `POST /api/admin/content/approve/1`

**Success Response (200):**
```json
{
  "statusCode": 200,
  "data": {
    "id": 1,
    "title": "Summer Sale 2025",
    "status": "APPROVED",
    "approvedByUsername": "johnadmin",
    "approvedByName": "John Admin",
    "approvedDate": "2025-10-13T02:00:00"
  },
  "message": "Content approved successfully and is now live"
}
```

---

### 5. Reject Content
**Endpoint:** `POST /api/admin/content/reject`
**Description:** Reject pending content with reason

**Request Body:**
```json
{
  "contentId": 1,
  "rejectionReason": "Images are not clear. Please upload high-quality images and resubmit."
}
```

**Success Response (200):**
```json
{
  "statusCode": 200,
  "data": {
    "id": 1,
    "title": "Summer Sale 2025",
    "status": "REJECTED",
    "rejectionReason": "Images are not clear. Please upload high-quality images and resubmit.",
    "approvedByUsername": "johnadmin"
  },
  "message": "Content rejected"
}
```

---

## Content Manager APIs

**Authorization Required:** `Bearer <JWT_TOKEN>`
**Required Role:** `CONTENT_MANAGER`

### 1. Create Content
**Endpoint:** `POST /api/content`
**Description:** Create new content (status: PENDING)

**Request Body:**
```json
{
  "title": "Summer Sale 2025",
  "description": "Get up to 50% off on all products! Limited time offer.",
  "posterUrl": "https://cdn.example.com/posters/summer-sale.jpg",
  "posterFilename": "summer-sale.jpg"
}
```

**Success Response (201):**
```json
{
  "statusCode": 201,
  "data": {
    "id": 1,
    "title": "Summer Sale 2025",
    "description": "Get up to 50% off on all products! Limited time offer.",
    "posterUrl": "https://cdn.example.com/posters/summer-sale.jpg",
    "posterFilename": "summer-sale.jpg",
    "status": "PENDING",
    "createdByUsername": "sarahmanager",
    "createdDate": "2025-10-13T01:00:00"
  },
  "message": "Content created successfully and submitted for approval"
}
```

---

### 2. Get My Content
**Endpoint:** `GET /api/content/my-content`
**Description:** Get all content created by logged-in user

**Success Response (200):**
```json
{
  "statusCode": 200,
  "data": [
    {
      "id": 1,
      "title": "Summer Sale 2025",
      "status": "APPROVED",
      "approvedDate": "2025-10-13T02:00:00"
    },
    {
      "id": 2,
      "title": "Winter Collection",
      "status": "PENDING"
    },
    {
      "id": 3,
      "title": "Old Campaign",
      "status": "REJECTED",
      "rejectionReason": "Images not clear"
    }
  ],
  "message": "Content retrieved successfully"
}
```

---

### 3. Update Content
**Endpoint:** `PUT /api/content/{contentId}`
**Description:** Update own content (only pending/rejected)
**Path Parameters:**
- `contentId`: ID of content to update

**Request Body:**
```json
{
  "title": "Updated Summer Sale 2025",
  "description": "Get up to 70% off! Extended offer.",
  "posterUrl": "https://cdn.example.com/posters/summer-sale-updated.jpg",
  "posterFilename": "summer-sale-updated.jpg"
}
```

**Success Response (200):**
```json
{
  "statusCode": 200,
  "data": {
    "id": 1,
    "title": "Updated Summer Sale 2025",
    "status": "PENDING",
    "updatedDate": "2025-10-13T03:00:00"
  },
  "message": "Content updated and resubmitted for approval"
}
```

**Error Response (400):**
```json
{
  "statusCode": 400,
  "message": "Cannot update approved content"
}
```

---

### 4. Delete Content
**Endpoint:** `DELETE /api/content/{contentId}`
**Description:** Delete own content
**Path Parameters:**
- `contentId`: ID of content to delete

**Success Response (200):**
```json
{
  "statusCode": 200,
  "data": "Content deleted successfully",
  "message": "Content deleted successfully"
}
```

**Error Response (403):**
```json
{
  "statusCode": 403,
  "message": "You can only delete your own content"
}
```

---

### 5. Get Content by ID
**Endpoint:** `GET /api/content/{contentId}`
**Description:** Get specific content details
**Path Parameters:**
- `contentId`: ID of content to retrieve

**Success Response (200):**
```json
{
  "statusCode": 200,
  "data": {
    "id": 1,
    "title": "Summer Sale 2025",
    "description": "Get up to 50% off on all products!",
    "posterUrl": "https://cdn.example.com/posters/summer-sale.jpg",
    "status": "APPROVED",
    "createdByUsername": "sarahmanager",
    "approvedByUsername": "johnadmin",
    "createdDate": "2025-10-13T01:00:00",
    "approvedDate": "2025-10-13T02:00:00"
  },
  "message": "Content retrieved successfully"
}
```

---

## Public APIs

**No Authentication Required**

### Get Approved Content
**Endpoint:** `GET /api/content/public/approved`
**Description:** Get all approved content for public website display

**Success Response (200):**
```json
{
  "statusCode": 200,
  "data": [
    {
      "id": 1,
      "title": "Summer Sale 2025",
      "description": "Get up to 50% off on all products!",
      "posterUrl": "https://cdn.example.com/posters/summer-sale.jpg",
      "posterFilename": "summer-sale.jpg",
      "status": "APPROVED",
      "createdDate": "2025-10-13T01:00:00",
      "approvedDate": "2025-10-13T02:00:00"
    },
    {
      "id": 5,
      "title": "New Product Launch",
      "description": "Introducing our latest product line!",
      "posterUrl": "https://cdn.example.com/posters/new-product.jpg",
      "status": "APPROVED"
    }
  ],
  "message": "Approved content retrieved successfully"
}
```

---

## Response Status Codes

### Success Codes
| Code | Name | Description |
|------|------|-------------|
| 200 | SUCCESS | Request successful |
| 201 | CREATED | Resource created successfully |

### Client Error Codes
| Code | Name | Description |
|------|------|-------------|
| 400 | BAD_REQUEST | Invalid request data |
| 401 | UNAUTHORIZED | Authentication required |
| 403 | FORBIDDEN | Insufficient permissions |
| 404 | NOT_FOUND | Resource not found |
| 409 | CONFLICT | Resource already exists |

### Server Error Codes
| Code | Name | Description |
|------|------|-------------|
| 500 | INTERNAL_SERVER_ERROR | Server error |

---

## Content Status Values

| Status | Description |
|--------|-------------|
| **PENDING** | Content submitted, awaiting admin review |
| **APPROVED** | Content approved by admin, live on website |
| **REJECTED** | Content rejected by admin with reason |

---

## Role Permissions Matrix

| Endpoint | SUPER_ADMIN | ADMIN | CONTENT_MANAGER | PUBLIC |
|----------|-------------|-------|-----------------|--------|
| `/api/auth/**` | ✅ | ✅ | ✅ | ✅ |
| `/api/superadmin/**` | ✅ | ❌ | ❌ | ❌ |
| `/api/admin/**` | ✅ | ✅ | ❌ | ❌ |
| `/api/content/**` | ✅ | ✅ | ✅ | ❌ |
| `/api/content/public/**` | ✅ | ✅ | ✅ | ✅ |

---

## Password Requirements

All passwords must meet these criteria:
- Minimum 8 characters
- Maximum 20 characters
- At least one uppercase letter (A-Z)
- At least one lowercase letter (a-z)
- At least one digit (0-9)
- At least one special character (@#$%^&+=!)

**Example Valid Password:** `Secure@123`

---

## Mobile Number Format

Indian mobile number format:
- Must start with 6, 7, 8, or 9
- Must be exactly 10 digits
- Pattern: `^[6-9][0-9]{9}$`

**Example:** `9876543210`

---

## PAN Number Format

Indian PAN card format:
- 5 uppercase letters
- 4 digits
- 1 uppercase letter
- Pattern: `[A-Z]{5}[0-9]{4}[A-Z]{1}`

**Example:** `ABCDE1234F`

---

## GST Number Format

Indian GST number format:
- 15 alphanumeric characters
- Pattern: `^[0-9A-Z]{15}$`

**Example:** `29ABCDE1234F1Z5`

---

**📖 For setup instructions, see [SETUP_GUIDE.md](SETUP_GUIDE.md)**
