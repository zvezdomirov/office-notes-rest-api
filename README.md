# Office Notes REST API

## Functional Requirements

### Authentication (Basic auth, persisted in a DB required) done in SecurityConfig

#### Registration (Sign Up)
- Username
- Password
- A default dev role should be set

In the database, there is a preloaded default Admin

#### Login

#### Logout

#### User management
##### 3 User Roles:
- Admin
- Manager
- Dev

- Each dev should be in only 1 team
- Each team has 1 manager
- Managers can be in multiple teams
- An admin can change the role of a user

#### Notes
- A dev can create a note visible for the whole company
- A dev can create a note visible for his team only
- A dev can edit his own notes
- A dev can delete his own notes
- A dev can see all company notes and his team notes
- A manager can edit all notes in his teams
- A manager can delete all notes in his teams
- A manager can do what a dev can

##### A note has:
- Title
- Description
- DateTime createdAt
- DateTIme deadline
- User createdBy

- Notes should be retrieved by pages by specifying a page number and a limit (page 1 - notes 1-10,
page 2 - notes 11-20, etc.)
