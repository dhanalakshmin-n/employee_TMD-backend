INSERT INTO employees (name, email, department, created_at)
SELECT 'John Doe', 'employee@test.com', 'Engineering', NOW()
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE email = 'employee@test.com');

INSERT INTO employees (name, email, department, created_at)
SELECT 'Sarah Smith', 'sarah@company.com', 'Marketing', NOW()
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE email = 'sarah@company.com');

INSERT INTO employees (name, email, department, created_at)
SELECT 'Mike Johnson', 'mike@company.com', 'HR', NOW()
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE email = 'mike@company.com');

INSERT INTO tasks (title, description, priority, status, due_date, assigned_employee_id, created_at)
SELECT 'Fix login bug', 'Resolve authentication issue on mobile devices', 'HIGH', 'PENDING', CURRENT_DATE + INTERVAL '30 days', e.id, NOW()
FROM employees e
WHERE e.email = 'employee@test.com'
  AND NOT EXISTS (SELECT 1 FROM tasks t WHERE t.title = 'Fix login bug');

INSERT INTO tasks (title, description, priority, status, due_date, assigned_employee_id, created_at)
SELECT 'Update dashboard UI', 'Improve manager dashboard layout and charts', 'MEDIUM', 'IN_PROGRESS', CURRENT_DATE + INTERVAL '14 days', e.id, NOW()
FROM employees e
WHERE e.email = 'employee@test.com'
  AND NOT EXISTS (SELECT 1 FROM tasks t WHERE t.title = 'Update dashboard UI');

INSERT INTO tasks (title, description, priority, status, due_date, assigned_employee_id, created_at)
SELECT 'Prepare HR report', 'Compile monthly HR metrics report', 'LOW', 'COMPLETED', CURRENT_DATE + INTERVAL '7 days', e.id, NOW()
FROM employees e
WHERE e.email = 'sarah@company.com'
  AND NOT EXISTS (SELECT 1 FROM tasks t WHERE t.title = 'Prepare HR report');

INSERT INTO tasks (title, description, priority, status, due_date, assigned_employee_id, created_at)
SELECT 'Onboard new hires', 'Complete onboarding checklist for new employees', 'MEDIUM', 'PENDING', CURRENT_DATE + INTERVAL '21 days', e.id, NOW()
FROM employees e
WHERE e.email = 'mike@company.com'
  AND NOT EXISTS (SELECT 1 FROM tasks t WHERE t.title = 'Onboard new hires');
