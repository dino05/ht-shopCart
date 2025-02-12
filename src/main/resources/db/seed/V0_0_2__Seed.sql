INSERT INTO cart (user_id) VALUES
    (1),(2),(3),(4),(5);

INSERT INTO cart_item (cart_id,offer_id,quantity,create_date,action_type) VALUES
	 (1,1111,3,'2024-06-20','ADD'),
	 (1,2222,5,'2024-06-20','ADD'),
	 (1,3333,2,'2024-06-20','ADD'),
	 (2,4444,2,'2024-06-20','ADD'),
	 (2,5555,2,'2024-06-20','ADD'),
	 (3,0000,1,'2024-06-20','ADD'),
	 (4,1111,1,'2024-06-20','ADD'),
	 (5,2222,1,'2024-06-20','ADD');

INSERT INTO item_price (price,recurrences,price_type,cart_item_id) VALUES
    (100.00, NULL, 'UPFRONT', 1),
    (200.00, 20, 'RECURRING', 2),
    (300.00, NULL, 'UPFRONT', 3),
    (400.00, 50, 'RECURRING', 4),
    (500.00, NULL, 'UPFRONT', 5),
    (600.00, NULL, 'UPFRONT', 6),
    (700.00, 120, 'RECURRING', 7),
    (800.00, NULL, 'UPFRONT', 8);