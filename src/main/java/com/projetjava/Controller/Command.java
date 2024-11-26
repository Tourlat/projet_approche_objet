package com.projetjava.Controller;

import com.projetjava.Model.Model;

public interface Command {
    void exec(Model model);
}
