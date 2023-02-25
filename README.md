# Execute For development

To run the backend, execute the following commands:

## Backend

First you need to populate the `.env` file on `backend` folder with the correct values,
following the README of that directory.

```bash
cd backend
docker-compose up -d
```

## Stable diffusion

If you want to enable stable diffusion, you need a self hosted instance of it.

Check out https://github.com/AUTOMATIC1111/stable-diffusion-webui

## Frontend

```bash
cd frontend
npm run dev
```

Now you can access the frontend at http://localhost:3000
