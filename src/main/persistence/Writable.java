package persistence;

import org.json.JSONObject;

// References: inspiration taken from the JsonSerializationDemo project
//             https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
