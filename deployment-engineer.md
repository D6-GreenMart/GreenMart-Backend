## Free-Tier Deployment Strategy (Long-Term)

Since you want a solution that lasts longer than a 90-day trial, here is the recommended stack for **permanent free tiers**:

### 1. Frontend (React) -> **Vercel** or **Netlify**
- **Cost**: Free forever for personal projects.
- **Why**: Excellent performance, automatic HTTPS, and git integration. Easiest setup.

### 2. Backend (Spring Boot) -> **Render**
- **Cost**: Free Individual Plan.
- **Why**: Supports Docker natively.
- **Limitation**: The free instance "spins down" after 15 minutes of inactivity. The first request after that will take ~30-60 seconds to load. This is standard for free JAVA hosting.

### 3. Database (MySQL) -> **Aiven** or **TiDB Cloud**
- **Aiven**: Offers a free MySQL plan (limited size).
- **TiDB Cloud**: Serverless MySQL-compatible DB with 5GB free storage.
- **Advice**: Avoid running the DB on the same container as the backend in free tiers as it consumes too much RAM.

### Recommended Action Plan
1.  **Frontend**: Deploy to Vercel (connect GitHub repo).
2.  **Database**: Create a free account on **Aiven** and get the `DB_URL`.
3.  **Backend**: Deploy to **Render** using the `Dockerfile` we created, passing the Aiven credentials as environment variables.

### Option 2: TiDB Cloud (Serverless MySQL) - **Highly Recommended**
If Aiven remains unavailable, **TiDB Cloud** is a fantastic alternative with 5GB free.

#### Step 1: Create Cluster (If you haven't yet)
-   **Cluster Name**: `GreenMart-Cluster`
-   **Region**: Choose `Mumbai` or `Singapore` (closest to you).
-   **Tier**: Ensure **Serverless** is selected (Free).
-   **Root Password**: Create a strong password (e.g., `GreenMart@2024`) and save it.

#### Step 2: Get Connection Details (The "Connect" Screen)
1.  Click **Connect** (Top Right).
2.  **Connection Type**: Select `Public`.
3.  **Branch**: Leave as `main`.
4.  **Connect With**: Select `General` or `Spring Boot`.
5.  **Operating System**: `Windows`.
6.  **Generate Password**: Click this button!
7.  **Copy these Values**:
    *   **Host**: ends with `...tidbcloud.com`
    *   **Port**: `4000`
    *   **User**: looks like `2AgP...root`
    *   **Password**: The one you just generated.

## Backend Deployment Guide

## Prerequisites
- Java 17 or higher (Required! Java 11 will cause build failure)
- Maven
- Docker (optional, for containerization)

## Environment Variables
The application requires the following environment variables to be set in production:

| Variable | Description | Default (Dev) |
|----------|-------------|---------------|
| `DB_URL` | JDBC URL for MySQL database | `jdbc:mysql://localhost:3306/greenmartdb...` |
| `DB_USERNAME` | Database username | `D6_86856_Yash` |
| `DB_PASSWORD` | Database password | `manager` |
| `JWT_SECRET` | Secret key for JWT signing | *(Pre-configured dev secret)* |

## Building the Application

### Using Maven
```bash
mvn clean package -DskipTests
```
The artifact will be generated at `target/greenmart-backend-0.0.1-SNAPSHOT.jar` (name may vary).

### Using Docker
```bash
docker build -t greenmart-backend .
docker run -p 8080:8080 -e DB_URL=... -e DB_USERNAME=... -e DB_PASSWORD=... greenmart-backend
```

## Cloud Deployment (Example: Railway/Render)
1. Connect your GitHub repository.
2. Set the **Root Directory** to `GreenMart-Backend` (if it's a monorepo) or leave as root if this repo is standalone.
3. Configure the environment variables listed above in the platform's dashboard.
4. The platform should automatically detect the `Dockerfile` or `pom.xml` and build deployment.
