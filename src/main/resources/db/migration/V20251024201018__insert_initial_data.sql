-- Insert default roles
INSERT INTO roles (id, name, description) VALUES
(UUID_TO_BIN(UUID()), 'ROLE_ADMIN', 'Administrator with full access'),
(UUID_TO_BIN(UUID()), 'ROLE_USER', 'Regular user with basic access');

-- Insert permissions for admin role
INSERT INTO role_permissions (role_id, permission)
SELECT id, 'USER_READ' FROM roles WHERE name = 'ROLE_ADMIN'
UNION ALL
SELECT id, 'USER_WRITE' FROM roles WHERE name = 'ROLE_ADMIN'
UNION ALL
SELECT id, 'USER_DELETE' FROM roles WHERE name = 'ROLE_ADMIN'
UNION ALL
SELECT id, 'ADMIN_READ' FROM roles WHERE name = 'ROLE_ADMIN'
UNION ALL
SELECT id, 'ADMIN_WRITE' FROM roles WHERE name = 'ROLE_ADMIN'
UNION ALL
SELECT id, 'ADMIN_DELETE' FROM roles WHERE name = 'ROLE_ADMIN';

-- Insert permissions for user role
INSERT INTO role_permissions (role_id, permission)
SELECT id, 'USER_READ' FROM roles WHERE name = 'ROLE_USER';