# Sweeter by the Dozen - Family Recipe Sharing Website

**Sweeter by the Dozen** is a family recipe sharing website designed to make it easy for users to share, save, and interact with their favorite recipes. The backend was created in **IntelliJ** using **Java** and **Spring Boot** to communicate with a **PostgreSQL** database. The frontend is built using **Vue.js**. 

The goal of this website is to provide a sleek and streamlined platform for recipe sharing, avoiding the clutter of endless text and images. Each recipe is displayed in a vintage recipe card layout that is simple to read and navigate. 

## Features

- **Recipe Sharing & Saving**: Users can share their favorite recipes and save their own to revisit later.
- **Commenting & Image Uploads**: Registered users can leave comments on recipes and upload images.
- **Advanced Search**: The search feature allows users to find recipes using both keywords and tags, providing unique and ordered results.
- **Admin Dashboard**: Admins have the ability to:
  - Approve or remove recipes, images, and flagged comments.
  - Edit recipes and manage user access.
  - Grant admin privileges to other users.
- **Two-Factor Authentication**: **Email.js** is used for two-factor authentication, password resets, and sending notifications.

## Technologies Used

 **Backend**: Java, Spring Boot, PostgreSQL
 **Frontend**: Vue.js
 **Authentication**: Email.js (for two-factor authentication and notifications)
 **Database**: PostgreSQL
 **Cloudinary**: User image uploads

## Roadmap

 **Current Status:**
 - Functional backend and frontend integration
 - User and admin roles implemented
 - Recipe management and search functionality
 **Future Enhancements:**
- Cohearant and accessible styling
- Recipe print options for hard copies
- Mobile app development
- Enhanced tagging and recommendation algorithms
- Broader user testing and feedback integration

## Screenshots and Visuals

![Recipe Page Screenshot](https://res.cloudinary.com/dzszygxtw/image/upload/v1732318296/recipe_h0aw4v.png)
![Comments on Recipe Page Screenshot](https://res.cloudinary.com/dzszygxtw/image/upload/v1732318299/display_comments_gzg2o3.png)
![Search Recipes Screenshot](https://res.cloudinary.com/dzszygxtw/image/upload/v1732318296/search_uqxexy.png)
![Submit a Recipe Screenshot](https://res.cloudinary.com/dzszygxtw/image/upload/v1732318297/submit_recipe_q52g8o.png)
![User Menu for Admin Screenshot](https://res.cloudinary.com/dzszygxtw/image/upload/v1732318297/user_menu_lviotb.png)
![Admin Tools Approve Recipe Screenshot](https://res.cloudinary.com/dzszygxtw/image/upload/v1732318297/approve_recipe_rt0npu.png)
![Admin Tools Edit Existing Recipe Screenshot](https://res.cloudinary.com/dzszygxtw/image/upload/v1732318299/edit_recipe_bm56ty.png)


## Running the Project Locally

1. Clone the repository:
   ```bash
   git clone https://github.com/kinseychristensen/sweeter-by-the-dozen.git
   cd sweeter-by-the-dozen
   npm install
   npm run dev
   ```

2. Navigate to the project folder:
```bash
cd sweeter-by-the-dozen
```

4. Install dependencies:
```bash
npm install
Run the backend server:
Open the backend directory and start the Spring Boot server.
Start the frontend:
npm run dev
```


## Feedback

This project is currently open for feedback. We welcome suggestions from beta testers to improve functionality and user experience.

## Acknowledgments

Special thanks to all contributors and beta testers for their support and insights. This project was inspired by the joy of sharing family recipes and the desire to make them easily accessible for generations to come.
