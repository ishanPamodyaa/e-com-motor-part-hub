-- Insert parent categories
INSERT INTO categories (name, description) VALUES 
('Engine Parts', 'Parts related to motorcycle engines'),
('Exhaust Systems', 'Exhaust pipes, mufflers, and related components'),
('Body Parts', 'Fairings, covers, and other body components'),
('Electrical Components', 'Batteries, lighting, and electrical systems'),
('Brakes', 'Brake pads, rotors, and brake systems'),
('Suspension', 'Forks, shock absorbers, and related parts'),
('Wheels & Tires', 'Wheels, tires, and related accessories'),
('Transmission', 'Gears, chains, sprockets, and clutch parts');

-- Insert subcategories for Engine Parts
INSERT INTO categories (name, description, parent_id) VALUES 
('Pistons', 'Pistons and piston kits', 1),
('Cylinder Kits', 'Cylinder blocks and kits', 1),
('Crankshafts', 'Crankshafts and crankshaft parts', 1),
('Camshafts', 'Camshafts and valve train components', 1),
('Engine Bearings', 'Various engine bearings', 1),
('Gaskets', 'Engine gaskets and seals', 1);

-- Insert subcategories for Exhaust Systems
INSERT INTO categories (name, description, parent_id) VALUES 
('Full Systems', 'Complete exhaust systems', 2),
('Slip-ons', 'Slip-on mufflers', 2),
('Headers', 'Exhaust headers and pipes', 2),
('Mufflers', 'Replacement mufflers', 2),
('Exhaust Accessories', 'Exhaust gaskets, wraps, and other accessories', 2);

-- Insert common tags
INSERT INTO tags (name) VALUES 
('performance'), 
('OEM'), 
('aftermarket'), 
('racing'), 
('sport'), 
('touring'), 
('cruiser'), 
('off-road'), 
('vintage'), 
('sale'), 
('premium'), 
('budget'), 
('lightweight'), 
('durable'), 
('waterproof'); 