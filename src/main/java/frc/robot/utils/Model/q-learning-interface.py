from networktables import NetworkTables
import time
import q_learning

# Initialize NetworkTables
NetworkTables.initialize(server='roborio-XXXX-frc.local')
q_table = NetworkTables.getTable("Q-learning")

# Load Q-learning model
q_learning_model = q_learning.QLearning(state_size=4, action_size=4)
q_learning_model.load_model("q_model.pkl")

while True:
    # Read state from NetworkTables
    xPos = q_table.getEntry("xPos").getDouble(0)
    yPos = q_table.getEntry("yPos").getDouble(0)
    heading = q_table.getEntry("heading").getDouble(0)
    targetVisible = q_table.getEntry("targetVisible").getBoolean(False)

    state = (xPos, yPos, heading, int(targetVisible))
    action = q_learning_model.choose_action(state)

    # Send action back to robot
    q_table.getEntry("action").setDouble(action)
    time.sleep(0.1)
