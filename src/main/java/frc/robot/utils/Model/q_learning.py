import numpy as np
import random
import pickle

class QLearning:
    def __init__(self, state_size, action_size, learning_rate=0.1, discount=0.9, epsilon=1.0, epsilon_decay=0.99):
        self.state_size = state_size
        self.action_size = action_size
        self.learning_rate = learning_rate
        self.discount = discount
        self.epsilon = epsilon
        self.epsilon_decay = epsilon_decay
        self.q_table = np.zeros((state_size, action_size))

    def choose_action(self, state):
        if random.uniform(0, 1) < self.epsilon:
            return random.randint(0, self.action_size - 1)  # Explore
        else:
            return np.argmax(self.q_table[state])  # Exploit

    def update_q_value(self, state, action, reward, next_state):
        best_next_action = np.argmax(self.q_table[next_state])
        q_update = reward + self.discount * self.q_table[next_state, best_next_action]
        self.q_table[state, action] += self.learning_rate * (q_update - self.q_table[state, action])
        self.epsilon *= self.epsilon_decay

    def save_model(self, file_name):
        with open(file_name, 'wb') as f:
            pickle.dump(self.q_table, f)

    def load_model(self, file_name):
        with open(file_name, 'rb') as f:
            self.q_table = pickle.load(f)
