# Laundry Service Management - Agile Backlog & User Stories (Day 01)

## Project Overview
- **Domain:** Services (Laundry & Dry Cleaning Management)
- **Complexity:** Moderate
- **Target Roles:** Admin, Staff, Delivery Agent, Customer

---

## Definition of Done (DoD)
A User Story is considered **Done** only when:
1. **Requirements & Logic:** All acceptance criteria are fully met with proper business validation.
2. **Code Standards:** Code follows clean Java conventions, proper encapsulation, and meaningful naming.
3. **Exception Safety:** Business constraints are guarded by domain exceptions; invalid user actions produce clear, non-crashing feedback.
4. **Data Integrity:** Item tags are globally unique (`Set`), state transitions follow strict workflows, and pricing changes are non-retroactive.
5. **Testing & Execution:** Console UI flows work end-to-end without unhandled runtime exceptions.
6. **Version Control:** Changes are committed to Git with clear, descriptive commit messages.

---

## User Stories & Story Point Estimates

| Story ID | Requirement | User Story ("As a <role>, I want ... so that ...") | Story Points | Priority |
| :--- | :--- | :--- | :---: | :---: |
| **US-01** | **FR1** | **As an Admin**, I want to create and update laundry services and item prices, **so that** the laundry system maintains an up-to-date and accurate price catalog. | 5 | High |
| **US-02** | **FR2** | **As a Customer**, I want to schedule a laundry pickup by specifying my address, slot, and delivery speed (Standard / Express), **so that** a delivery agent can collect my clothes conveniently. | 5 | High |
| **US-03** | **FR3** | **As a Staff Member**, I want to inspect collected garments, record item categories/quantities, and generate unique per-item tags, **so that** items are accurately tracked throughout the cleaning process without misplacement. | 8 | Critical |
| **US-04** | **FR4** | **As a System / Customer**, I want the system to calculate the complete itemized bill including express surcharge strategies and taxes, **so that** transparent billing is generated for customers. | 5 | High |
| **US-05** | **FR5** | **As a Staff Member**, I want to update the processing stages (Wash -> Iron -> Pack) with validated transition rules, **so that** laundry progresses systematically and bottlenecks are avoided. | 5 | High |
| **US-06** | **FR6** | **As a Delivery Agent**, I want to view my pickup and delivery queue and mark pickups/deliveries as completed, **so that** logistics milestones are accurately tracked in real-time. | 3 | Medium |
| **US-07** | **FR7** | **As a Customer**, I want to track my order status and view current processing stages using my Order ID, **so that** I know exactly when my garments will be delivered. | 3 | Medium |
| **US-08** | **FR8** | **As an Admin**, I want to view a daily production report summarizing orders received, items processed per stage, and daily revenue, **so that** I can assess operational productivity and daily revenue. | 5 | Medium |

**Total Backlog Story Points:** 39 SP

---

### Detailed Acceptance Criteria per User Story

#### US-01 (FR1 - Service & Price Management)
- **Acceptance Criteria:**
  - Admin can list all active services (e.g., Wash & Fold, Wash & Iron, Dry Clean, Express Press).
  - Admin can add a new item type and price.
  - Updating a price does not retroactively change existing orders (NFR-P4).
  - Price must be greater than zero.

#### US-02 (FR2 - Pickup Scheduling)
- **Acceptance Criteria:**
  - Customer can specify customer name, contact, pickup address, and preferred time slot.
  - Customer can select Standard service or Express service.
  - Pickup request is enqueued into the logistics pickup queue in FIFO order.
  - System issues a unique Pickup Request ID.

#### US-03 (FR3 - Item Recording & Unique Tagging)
- **Acceptance Criteria:**
  - Staff selects an order and records item lines (e.g., Shirt, Trouser, Silk Saree, Blanket).
  - System auto-generates a unique alphanumeric tag for each individual garment (e.g., `TAG-ORD101-SHIRT-01`).
  - Tags are guaranteed unique across the whole system using a Set; duplicate generation triggers validation failure (NFR-P1).
  - Staff can review recorded items and attached tags.

#### US-04 (FR4 - Bill Calculation & Express Surcharge)
- **Acceptance Criteria:**
  - Base bill is calculated using item-wise prices recorded at order creation time.
  - Standard service uses Standard Surcharge Strategy (0% extra).
  - Express service uses Express Surcharge Strategy (+50% surcharge).
  - Final invoice includes base total, surcharge, taxes (5% GST), and grand total.

#### US-05 (FR5 - Processing Stage Progression)
- **Acceptance Criteria:**
  - Allowed stages follow strict workflow: `RECEIVED` -> `WASHING` -> `IRONING` -> `PACKING` -> `READY_FOR_DELIVERY` -> `DELIVERED`.
  - Transitions cannot skip stages or regress to prior stages (NFR-P2).
  - Stage update logs timestamp and acting staff member.

#### US-06 (FR6 - Pickup & Delivery Completion)
- **Acceptance Criteria:**
  - Agent can view pending pickups and deliveries.
  - Completing a pickup transitions order to `RECEIVED` stage and adds garments to the workshop inventory.
  - Completing delivery updates order status to `DELIVERED`.

#### US-07 (FR7 - Order Status Tracking)
- **Acceptance Criteria:**
  - Customer enters Order ID to view current stage, items, pickup details, and delivery ETA.
  - Displays clear error if Order ID is invalid or not found.

#### US-08 (FR8 - Daily Production Report)
- **Acceptance Criteria:**
  - Admin can view aggregate statistics: total orders, total garments processed, counts per stage, express vs standard breakdown, and total revenue billed.
