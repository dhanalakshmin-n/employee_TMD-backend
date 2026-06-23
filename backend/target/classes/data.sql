INSERT INTO employees (name, email, department, created_at)
SELECT 'John Doe', 'employee@test.com', 'Engineering', NOW()
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE email = 'employee@test.com');

INSERT INTO employees (name, email, department, created_at)
SELECT 'Sarah Smith', 'sarah@company.com', 'Marketing', NOW()
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE email = 'sarah@company.com');

INSERT INTO employees (name, email, department, created_at)
SELECT 'Mike Johnson', 'mike@company.com', 'HR', NOW()
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE email = 'mike@company.com');
